package com.bbg.admin.base.controller.sys;

import com.bbg.admin.base.BaseController;
import com.bbg.model.sys.SysUser;
import com.bbg.core.service.sys.SysUserService;
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
 * 系统用户 控制层。
 *
 * @author bbg
 * @since 2024-04-26
 */
@Tag(name = "系统用户接口")
@RequestMapping("/sysUser")
public class BaseSysUserController extends BaseController<SysUser, SysUserService> {
    @Autowired
    protected  SysUserService sysUserService;

    @PostMapping("save")
    @Operation(summary = "保存", description = "保存")
    public ApiRet<Boolean> save(@RequestBody SysUser model) {
        return ApiRet.buildOk(sysUserService.save(model));
    }

    @GetMapping("remove/{id}")
    @Operation(summary = "根据主键删除", description = "根据主键删除")
    public ApiRet<Boolean> remove(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(sysUserService.removeById(id));
    }

    @PostMapping("update")
    @Operation(summary = "根据主键更新", description = "根据主键更新")
    public ApiRet<Boolean> update(@RequestBody @Parameter(description = "业务主键") SysUser model) {
        return ApiRet.buildOk(sysUserService.updateById(model));
    }

    @GetMapping("getInfo/{id}")
    @Operation(summary = "根据主键获取", description = "根据主键获取")
    public ApiRet<SysUser> getInfo(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(sysUserService.getById(id));
    }

    @PostMapping("list")
    Operation(summary = "查询所有", description = "查询所有")
    public ApiRet<List<SysUser>> list(@RequestBody SysUser model) {
        return ApiRet.buildOk(sysUserService.list(QueryWrapper.create(model)));
    }

    @PostMapping("page")
    Operation(summary = "分页查询", description = "分页查询")
    public ApiRet<Page<SysUser>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<SysUser> reqParams) {
        // Entity 转查询条件
        SqlOperators operators = SqlOperators.of().set(SysUser::getId, SqlOperator.EQUALS);
        QueryWrapper queryWrapper = reqParams.getQueryEntity() == null ? QueryWrapper.create() : QueryWrapper.create(reqParams.getQueryEntity(), operators);
        queryWrapper = super.buildQueryWrapper(queryWrapper, reqParams.getQueryEntity());
        return ApiRet.buildOk(sysUserService.page(reqParams.getPage(), queryWrapper));
    }
}