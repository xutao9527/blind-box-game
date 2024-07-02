package com.bbg.admin.controller.sys;

import com.bbg.admin.base.controller.sys.BaseSysUserController;
import com.bbg.core.service.sys.*;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.record.SysMenuRecord;
import com.bbg.model.record.SysRoleMenuRecord;
import com.bbg.model.sys.*;
import com.mybatisflex.core.mask.MaskManager;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.tenant.TenantManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统用户 控制层。
 *
 * @author bbg
 * @since 2024-04-25
 */
@RestController
@Tag(name = "系统用户接口")
@RequestMapping("/sysUser")
@RequiredArgsConstructor
public class SysUserController extends BaseSysUserController {

    public final SysMenuService sysMenuService;
    public final SysUserRoleService sysUserRoleService;
    public final SysRoleMenuService sysRoleMenuService;
    public final SysRoleService sysRoleService;
    public final SysTenantService sysTenantService;

    @PostMapping("login")
    @Operation(summary = "管理员登录", description = "管理员登录")
    public ApiRet<String> login(@RequestBody SysUser sysUser) {
        ApiRet<String> ret;
        QueryWrapper queryWrapper = QueryWrapper.create().and(SysUser::getAccount).eq(sysUser.getAccount());
        SysUser user = TenantManager.withoutTenantCondition(
                () -> MaskManager.execWithoutMask(() -> sysUserService.getOne(queryWrapper))
        );
        if (user != null && user.getEnable() && user.getPassword().equals(sysUser.getPassword())) {
            user.setPassword(null);
            // 判断是不是超级租户
            SysTenant currentTenant = sysTenantService.getById(user.getTenantId());
            if (currentTenant.getParentId() == null) {
                user.setSuperTenant(true);
                user.setTenantMap(sysTenantService.list().stream().collect(Collectors.toMap(SysTenant::getId, sysTenant -> sysTenant)));
            }
            String token = redisService.adminLogin(user);
            ret = ApiRet.buildOk(token);
        } else if(user == null){
            ret = ApiRet.buildNo(null, "用户不存在!");
        }else{
            ret = ApiRet.buildNo(null, "密码错误!");
        }
        return ret;
    }

    @GetMapping("logout")
    @Operation(summary = "管理员登出", description = "管理员登出")
    public ApiRet<String> logout() {
        String token = request.getHeader("token");
        if (token != null) {
            redisService.adminLogout(token);
        }
        return ApiRet.buildOk(token);
    }

    @PostMapping("authUserRole")
    @Operation(summary = "授权用户的角色", description = "授权用户的角色")
    public ApiRet<Boolean> authUserRole(@RequestBody SysUserRole sysUserRole) {
        sysUserRoleService.remove(QueryWrapper.create(new SysUserRole().setUserId(sysUserRole.getUserId())));
        return ApiRet.buildOk(sysUserRoleService.save(sysUserRole));
    }

    @PostMapping("getUserRole")
    @Operation(summary = "获得用户的角色", description = "获得用户的角色")
    public ApiRet<SysRole> getUserRole(@RequestBody SysUserRole sysUserRole) {
        SysRole sysRole = null;
        SysUserRole userRole = sysUserRoleService.getOne(QueryWrapper.create(new SysUserRole().setUserId(sysUserRole.getUserId())));
        if (userRole != null) {
            sysRole = sysRoleService.getById(userRole.getRoleId());
        }
        return ApiRet.buildOk(sysRole);
    }

    @PostMapping("authRoleMenu")
    @Operation(summary = "授权角色的菜单", description = "授权角色的菜单")
    public ApiRet<Boolean> authRoleMenu(@RequestBody List<SysRoleMenu> roleMenuList) {
        boolean result = false;
        if (roleMenuList != null && !roleMenuList.isEmpty()) {
            sysRoleMenuService.remove(QueryWrapper.create(new SysRoleMenu().setRoleId(roleMenuList.get(0).getRoleId())));
            result = sysRoleMenuService.saveBatch(roleMenuList);
        }
        return ApiRet.buildOk(result);
    }

    @PostMapping("getRoleMenu")
    @Operation(summary = "获得角色的菜单", description = "获得角色的菜单")
    public ApiRet<List<SysRoleMenu>> getRoleMenu(@RequestBody SysRoleMenu sysRoleMenu) {
        QueryWrapper queryWrapper = QueryWrapper.create(new SysRoleMenu().setRoleId(sysRoleMenu.getRoleId()));
        List<SysRoleMenu> sysMenuList = sysRoleMenuService.list(queryWrapper);
        return ApiRet.buildOk(sysMenuList);
    }

    @GetMapping("currentUserMenu")
    @Operation(summary = "当前管理员菜单", description = "当前管理员菜单")
    public ApiRet<List<SysMenu>> currentUserMenu() {
        SysUser sysUser = null;
        List<SysMenu> sysMenus = null;
        String token = request.getHeader("token");
        if (token != null) {
            sysUser = redisService.getAdmin(token);
        }
        // 判断是不是超级管理员
        if (sysUser != null && sysUser.getSuperAdmin()) {
            sysMenus = sysMenuService.list(QueryWrapper.create(new SysMenu().setEnable(true)));
        } else if (sysUser != null) {
            SysUserRole sysUserRole = sysUserRoleService.getOne(QueryWrapper.create(new SysUserRole().setUserId(sysUser.getId())));
            if (sysUserRole != null) {
                List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(QueryWrapper.create(new SysRoleMenu().setRoleId(sysUserRole.getRoleId())));
                if (sysRoleMenuList != null && !sysRoleMenuList.isEmpty()) {
                    sysMenus = sysMenuService.list(QueryWrapper.create().in(SysMenu::getId, sysRoleMenuList.stream().map(SysRoleMenuRecord::getMenuId).toList()));
                }
            }
        }
        // 不是顶级租户,则过滤顶级租户菜单与接口
        if (sysUser != null && sysMenus != null && !sysUser.isSuperTenant()) {
            sysMenus = sysMenus.stream().filter(sysMenu -> !sysMenu.getTenantPermissions() && sysMenu.getEnable()).collect(Collectors.toList());
        }
        redisService.updateAdminPermission(token, sysMenus);
        return sysUser == null ? ApiRet.buildNo(null, "令牌失效") : ApiRet.buildOk(sysMenus);
    }

    @GetMapping("currentUser")
    @Operation(summary = "当前管理员信息", description = "当前管理员信息")
    public ApiRet<SysUser> currentUser() {
        SysUser sysUser = null;
        String token = request.getHeader("token");
        if (token != null) {
            sysUser = redisService.getAdmin(token);
        }
        return sysUser == null ? ApiRet.buildNo(null, "令牌失效") : ApiRet.buildOk(sysUser);
    }
}