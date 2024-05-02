package com.bbg.admin.base.controller.sys;

import com.bbg.admin.base.BaseController;
import com.bbg.model.sys.SysRole;
import com.bbg.admin.service.sys.SysRoleService;
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
 * 系统角色 控制层。
 *
 * @author bbg
 * @since 2024-04-26
 */
@Tag(name = "系统角色接口")
@RequestMapping("/sysRole")
public class BaseSysRoleController extends BaseController<SysRole, SysRoleService> {
    @Autowired
    protected  SysRoleService sysRoleService;

    @PostMapping("save")
    @Operation(description = "保存")
    public ApiRet<Boolean> save(@RequestBody SysRole model) {
        return ApiRet.buildOk(sysRoleService.save(model));
    }

    @GetMapping("remove/{id}")
    @Operation(description = "根据主键删除")
    public ApiRet<Boolean> remove(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(sysRoleService.removeById(id));
    }

    @PostMapping("update")
    @Operation(description = "根据主键更新")
    public ApiRet<Boolean> update(@RequestBody @Parameter(description = "业务主键") SysRole model) {
        return ApiRet.buildOk(sysRoleService.updateById(model));
    }

    @GetMapping("getInfo/{id}")
    @Operation(description = "根据主键获取")
    public ApiRet<SysRole> getInfo(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(sysRoleService.getById(id));
    }

    @PostMapping("list")
    @Operation(description = "查询所有")
    public ApiRet<List<SysRole>> list(@RequestBody SysRole model) {
        return ApiRet.buildOk(sysRoleService.list(QueryWrapper.create(model)));
    }

    @PostMapping("page")
    @Operation(description = "分页查询")
    public ApiRet<Page<SysRole>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<SysRole> reqParams) {
        // Entity 转查询条件
        SqlOperators operators = SqlOperators.of()
                .set(SysRole::getId, SqlOperator.EQUALS)
                .set(SysRole::getName,SqlOperator.LIKE);
        QueryWrapper queryWrapper = reqParams.getQueryEntity() == null ? QueryWrapper.create() : QueryWrapper.create(reqParams.getQueryEntity(), operators);
        queryWrapper = super.buildQueryWrapper(queryWrapper, reqParams.getQueryEntity());
        return ApiRet.buildOk(sysRoleService.page(reqParams.getPage(), queryWrapper));
    }
}