package com.bbg.admin.controller.sys;

import com.bbg.admin.base.controller.sys.BaseSysMenuController;
import com.bbg.admin.third.api.SyncLocalApiService;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.ReqParams;
import com.bbg.model.record.SysMenuRecord;
import com.bbg.model.sys.SysMenu;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统菜单 控制层。
 *
 * @author bbg
 * @since 2024-04-25
 */
@Aspect
@RestController
@Tag(name = "系统菜单接口")
@RequestMapping("/sysMenu")
@RequiredArgsConstructor
public class SysMenuController extends BaseSysMenuController {

    public final SyncLocalApiService syncLocalApiService;

    @Override
    @PostMapping("page")
    @Operation(summary = "分页查询", description = "分页查询")
    public ApiRet<Page<SysMenu>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<SysMenu> reqParams) {
        return super.page(reqParams);
    }

    @GetMapping("getSelectMenus")
    @Operation(summary = "获得前端菜单", description = "获得前端菜单")
    public ApiRet<List<SysMenu>> getSelectMenus() {
        List<SysMenu> list = sysMenuService.list(QueryWrapper.create().and(SysMenu::getType).eq("1"));
        return ApiRet.buildOk(list);
    }


    @GetMapping("syncPermission")
    @Operation(summary = "同步权限数据", description = "同步权限数据")
    public ApiRet<Boolean> syncPermission() {
        return ApiRet.buildOk(syncLocalApiService.syncData());
    }
}