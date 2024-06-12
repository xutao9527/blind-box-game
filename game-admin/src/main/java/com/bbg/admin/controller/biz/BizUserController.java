package com.bbg.admin.controller.biz;

import com.bbg.admin.base.controller.biz.BaseBizUserController;
import com.bbg.core.constrans.KeyConst;
import com.bbg.core.service.biz.BizDictService;
import com.bbg.core.service.biz.BizUserService;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoCapitalRecord;
import com.bbg.model.sys.SysUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 业务用户 控制层。
 *
 * @author bbg
 * @since 2024-04-25
 */
@RestController
@Tag(name = "业务用户接口")
@RequestMapping("/bizUser")
@RequiredArgsConstructor
@Validated
public class BizUserController extends BaseBizUserController {

    public final BizDictService bizDictService;

    @Operation(description = "业务用户后台充值")
    @GetMapping("updateBizUserMoney/{id}/{money}")
    public ApiRet<Boolean> updateBizUserMoney(
            @PathVariable(name = "id") @Parameter(description = "用户编号") Serializable id,
            @PathVariable(name = "money") @Parameter(description = "业务主键") BigDecimal money
    ) {
        SysUser sysUser = getCurrentUser();
        BizUser bizUser = bizUserService.getById(id);
        if (bizUser != null) {
            CsgoCapitalRecord capitalRecord = new CsgoCapitalRecord();
            capitalRecord.setUserId(bizUser.getId())
                    .setSourceId(sysUser.getId().toString())
                    .setType(bizDictService.getDictByTag("csgo_capital_type").getValueByAlias("admin_pay"))  // 流水类型
                    .setChangeMoney(money);
            bizUserService.updateUserMoney(bizUser, capitalRecord);
        }
        return ApiRet.buildOk(true);
    }

    @Operation(description = "批量新增虚拟用户")
    @GetMapping("addVirtualUser")
    public ApiRet<Boolean> addVirtualUser(
            @RequestParam("count")
            @Min(value = 1, message = "新增数量1~1000之间")
            @Max(value = 1000, message = "新增数量1~1000之间")
            @Schema(description = "新增数量")
            int count) {
        return ApiRet.buildOk(bizUserService.addVirtualUser(count));
    }

    @Operation(description = "后台查看验证码")
    @GetMapping("viewCode/{mobile}")
    public ApiRet<String> viewSmsCode(@PathVariable(name = "mobile") @Parameter(description = "手机号") @NotNull String mobile) {
        String code = (String) redisService.get(KeyConst.build(KeyConst.USER_SMS_CODE,mobile));
        if (code != null) {
            return ApiRet.buildOk(code);
        }
        return ApiRet.buildNo("验证码不存在");
    }
}