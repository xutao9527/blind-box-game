package com.bbg.admin.controller.sys;

import com.bbg.admin.base.controller.sys.BaseSysMenuController;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.ReqParams;
import com.bbg.model.record.SysMenuRecord;
import com.bbg.model.sys.SysMenu;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RestController
@Tag(name = "系统菜单接口")
@RequestMapping("/sysMenu")
@Aspect
public class SysMenuController extends BaseSysMenuController {

    @Override
    @PostMapping("page")
    @Operation(summary = "分页查询", description = "分页查询")
    public ApiRet<Page<SysMenu>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<SysMenu> reqParams) {
        ApiRet<Page<SysMenu>> apiRet = super.page(reqParams);
        List<SysMenu> records = apiRet.getData().getRecords();
        Map<Long, SysMenu> sysMenuMap = records.stream().collect(Collectors.toMap(SysMenu::getId, SysMenu -> SysMenu));
        records = records.stream().peek(sysMenu -> {
            if (sysMenu.getParentId() != null) {
                SysMenu parent = sysMenuMap.get(sysMenu.getParentId());
                if (sysMenu.getExpandProps() == null) {
                    sysMenu.setExpandProps(HashMap.newHashMap(1));
                }
                sysMenu.getExpandProps().put("parentTitle", Objects.isNull(parent) ? "" : parent.getTitle());
            }
        }).toList();
        List<Long> ids = records.stream()
                .filter(sysMenu -> sysMenu.getParentId() == null || sysMenu.getExpandProps() == null || sysMenu.getExpandProps().get("parentTitle") == null)
                .map(SysMenu::getId).toList();
        if (!ids.isEmpty()) {
            List<SysMenu> sysMenus = sysMenuService.listByIds(ids);
            records.forEach(sysMenu -> {
                if (sysMenu.getParentId() == null || sysMenu.getExpandProps() == null || sysMenu.getExpandProps().get("parentTitle") == null) {
                    SysMenu parent = sysMenus.stream().filter(menu -> menu.getId().equals(sysMenu.getParentId())).findFirst().orElse(null);
                    if (sysMenu.getExpandProps() == null) {
                        sysMenu.setExpandProps(HashMap.newHashMap(1));
                    }
                    sysMenu.getExpandProps().put("parentTitle", Objects.isNull(parent) ? "" : parent.getTitle());
                }
            });
        }
        return apiRet;
    }

    @GetMapping("getSelectMenus")
    @Operation(summary = "获得一二级菜单", description = "获得一二级菜单")
    public ApiRet<List<SysMenu>> getSelectMenus() {
        List<SysMenu> allMenus = new ArrayList<>();
        SysMenu rootMenu = sysMenuService.getOne(QueryWrapper.create(new SysMenu().setEnable(true)).isNull(SysMenu::getParentId));
        if(rootMenu!=null){
            allMenus = sysMenuService.list(QueryWrapper.create(new SysMenu().setEnable(true)).eq(SysMenu::getParentId, rootMenu.getId()));
            allMenus.addFirst(rootMenu);
        }
        return ApiRet.buildOk(allMenus);
    }
}