package com.bbg.admin.third.api;

import com.bbg.core.entity.ApiRet;
import com.bbg.core.feign.apidocs.ApiDocsService;
import com.bbg.core.service.sys.SysMenuService;
import com.bbg.model.sys.SysMenu;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SyncLocalApiService {

    public final ApiDocsService apiDocsService;
    public final SysMenuService sysMenuService;

    public boolean syncData() {
        Map<String, Object> apiDocs = apiDocsService.getDocs();
        List<SysMenu> sysMenuList = new ArrayList<>();
        if (apiDocs.get("paths") instanceof Map<?, ?> map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> paths = (Map<String, Object>) map;
            paths.forEach((path, value) -> {
                SysMenu sysMenu = new SysMenu();
                sysMenu.setPath(path);
                sysMenu.setName(path.replace("/", "_").replace("{", "").replace("}", "").substring(1));
                sysMenu.setEnable(true);
                sysMenu.setType("2");
                sysMenu.setTenantPermissions(false);
                @SuppressWarnings("unchecked")
                Map<String, Object> pathMap = (Map<String, Object>) value;
                pathMap.forEach((method, methodValue) -> {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> methodMap = (Map<String, Object>) methodValue;
                    String tag = "";
                    if (methodMap.get("tags") instanceof List<?> list) {
                        if (!list.isEmpty()) {
                            tag = list.getFirst().toString();
                            tag = tag.replace("接口", "");
                        }
                    }
                    String summary = methodMap.get("summary").toString();
                    summary = switch (summary) {
                        case "根据主键删除" -> "主键删除";
                        case "根据主键获取" -> "主键查询";
                        case "根据主键更新" -> "主键更新";
                        default -> summary;
                    };
                    sysMenu.setTitle(tag + "_" + summary);
                });
                sysMenuList.add(sysMenu);
            });
        }
        // 查询所有菜单项
        List<SysMenu> sysMenus = sysMenuService.list(QueryWrapper.create(new SysMenu().setType("1")));
        // 权限设置到菜单下
        sysMenuList.forEach(sysMenu -> {
            SysMenu parentMenu = sysMenus.stream().filter(
                            menu -> sysMenu.getPath().split("/")[1].equals(menu.getPath().split("/")[1])
                    )
                    .findFirst().orElse(null);
            SysMenu rootMenu = sysMenus.stream().filter(rm -> rm.getName().equals("root")).findFirst().orElse(null);
            if (parentMenu != null) {
                sysMenu.setParentId(parentMenu.getId());
            } else {
                sysMenu.setParentId(rootMenu == null ? null : rootMenu.getId());
            }
            // System.out.printf("%s \t %s \t %s \t %s", sysMenu.getTitle(), sysMenu.getName(), sysMenu.getPath() ,sysMenu.getParentId());
            // System.out.println();
        });
        sysMenuList.forEach((menu -> {
            SysMenu existMenu = sysMenuService.getOne(QueryWrapper.create(new SysMenu().setPath(menu.getPath())));
            if(existMenu!=null){
                existMenu.setParentId(menu.getParentId()).setName(menu.getName()).setTitle(menu.getTitle()).setType(menu.getType());
                sysMenuService.updateById(existMenu);
            }else{
                sysMenuService.save(menu);
            }
            System.out.printf("%s \t %s \t %s%n", menu.getTitle(),menu.getName(),menu.getPath());
        }));
        return true;
    }
}
