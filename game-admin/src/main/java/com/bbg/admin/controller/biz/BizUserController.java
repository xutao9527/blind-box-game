package com.bbg.admin.controller.biz;

import com.bbg.admin.base.controller.biz.BaseBizUserController;
import com.bbg.admin.service.biz.BizDictService;
import com.bbg.admin.service.biz.BizUserService;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoCapitalRecord;
import com.bbg.model.sys.SysUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 业务用户 控制层。
 *
 * @author bbg
 * @since 2024-04-25
 */
@RestController
@Tag(name = "业务用户接口")
@RequestMapping("/bizUser")
public class BizUserController extends BaseBizUserController {

    @Autowired
    BizDictService bizDictService;

    @Operation(description = "业务用户后台充值")
    @GetMapping("updateBizUserMoney/{id}/{money}")
    public ApiRet<Boolean> updateBizUserMoney(
            @PathVariable(name = "id") @Parameter(description = "用户编号") Serializable id,
            @PathVariable(name = "money") @Parameter(description = "业务主键") BigDecimal money
    ) {
        SysUser sysUser = getCurrentUser();
        BizUser bizUser = bizUserService.getById(id);
        if(bizUser!=null){
            CsgoCapitalRecord capitalRecord = new CsgoCapitalRecord();
            capitalRecord.setUserId(bizUser.getId())
                    .setSourceId(sysUser.getId().toString())
                    .setType(bizDictService.getDictByTag("csgo_capital_type").getValueByAlias("admin_pay"))  //流水类型
                    .setChangeMoney(money);
            bizUserService.updateUserMoney(bizUser,capitalRecord);
        }
        return ApiRet.buildOk(true);
    }

}