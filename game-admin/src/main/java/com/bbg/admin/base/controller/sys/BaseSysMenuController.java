package com.bbg.admin.base.controller.sys;

import com.bbg.admin.base.BaseController;
import com.bbg.model.sys.SysMenu;
import com.bbg.core.service.sys.SysMenuService;
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
 * 系统菜单 控制层。
 *
 * @author bbg
 * @since 2024-04-26
 */
@Tag(name = "系统菜单接口")
@RequestMapping("/sysMenu")
public class BaseSysMenuController extends BaseController<SysMenu, SysMenuService> {
    @Autowired
    protected SysMenuService sysMenuService;

    @PostMapping("save")
    @Operation(description = "保存")
    public ApiRet<Boolean> save(@RequestBody SysMenu model) {
        return ApiRet.buildOk(sysMenuService.save(model));
    }

    @GetMapping("remove/{id}")
    @Operation(description = "根据主键删除")
    public ApiRet<Boolean> remove(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(sysMenuService.removeById(id));
    }

    @PostMapping("update")
    @Operation(description = "根据主键更新")
    public ApiRet<Boolean> update(@RequestBody @Parameter(description = "业务主键") SysMenu model) {
        return ApiRet.buildOk(sysMenuService.updateById(model));
    }

    @GetMapping("getInfo/{id}")
    @Operation(description = "根据主键获取")
    public ApiRet<SysMenu> getInfo(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(sysMenuService.getById(id));
    }

    @PostMapping("list")
    @Operation(description = "查询所有")
    public ApiRet<List<SysMenu>> list(@RequestBody SysMenu model) {
        return ApiRet.buildOk(sysMenuService.list(QueryWrapper.create(model)));
    }

    @PostMapping("page")
    @Operation(description = "分页查询")
    public ApiRet<Page<SysMenu>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<SysMenu> reqParams) {
        // Entity 转查询条件
        SqlOperators operators = SqlOperators.of().set(SysMenu::getId, SqlOperator.EQUALS);
        QueryWrapper queryWrapper = reqParams.getQueryEntity() == null ? QueryWrapper.create() : QueryWrapper.create(reqParams.getQueryEntity(), operators);
        queryWrapper = super.buildQueryWrapper(queryWrapper, reqParams.getQueryEntity());
        queryWrapper.orderBy(SysMenu::getParentId, true);
        queryWrapper.orderBy(SysMenu::getSort, true);
        return ApiRet.buildOk(sysMenuService.page(reqParams.getPage(), queryWrapper));
    }
}