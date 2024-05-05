package com.bbg.admin.base.controller.csgo;

import com.bbg.admin.base.BaseController;
import com.bbg.model.csgo.CsgoUserInfo;
import com.bbg.admin.service.csgo.CsgoUserInfoService;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.ReqParams;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.constant.SqlOperator;
import com.mybatisflex.core.query.SqlOperators;
import lombok.RequiredArgsConstructor;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * CSGO用户信息 控制层。
 *
 * @author bbg
 * @since 2024-05-05
 */
@Tag(name = "CSGO用户信息接口")
@RequestMapping("/csgoUserInfo")
public class BaseCsgoUserInfoController extends BaseController<CsgoUserInfo, CsgoUserInfoService> {
    @Autowired
    protected  CsgoUserInfoService csgoUserInfoService;

    @PostMapping("save")
    @Operation(description = "保存")
    public ApiRet<Boolean> save(@RequestBody CsgoUserInfo model) {
        return ApiRet.buildOk(csgoUserInfoService.save(model));
    }

    @GetMapping("remove/{id}")
    @Operation(description = "根据主键删除")
    public ApiRet<Boolean> remove(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(csgoUserInfoService.removeById(id));
    }

    @PostMapping("update")
    @Operation(description = "根据主键更新")
    public ApiRet<Boolean> update(@RequestBody @Parameter(description = "业务主键") CsgoUserInfo model) {
        return ApiRet.buildOk(csgoUserInfoService.updateById(model));
    }

    @GetMapping("getInfo/{id}")
    @Operation(description = "根据主键获取")
    public ApiRet<CsgoUserInfo> getInfo(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(csgoUserInfoService.getById(id));
    }

    @PostMapping("list")
    @Operation(description = "查询所有")
    public ApiRet<List<CsgoUserInfo>> list(@RequestBody CsgoUserInfo model) {
        return ApiRet.buildOk(csgoUserInfoService.list(QueryWrapper.create(model)));
    }

    @PostMapping("page")
    @Operation(description = "分页查询")
    public ApiRet<Page<CsgoUserInfo>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<CsgoUserInfo> reqParams) {
        // Entity 转查询条件
        SqlOperators operators = SqlOperators.of().set(CsgoUserInfo::getId, SqlOperator.EQUALS);
        QueryWrapper queryWrapper = reqParams.getQueryEntity() == null ? QueryWrapper.create() : QueryWrapper.create(reqParams.getQueryEntity(), operators);
        queryWrapper = super.buildQueryWrapper(queryWrapper, reqParams.getQueryEntity());
        return ApiRet.buildOk(csgoUserInfoService.page(reqParams.getPage(), queryWrapper));
    }
}