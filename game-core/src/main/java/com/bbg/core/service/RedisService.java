package com.bbg.core.service;

import com.bbg.core.base.RedisBase;
import com.bbg.core.constrans.KeyConst;
import com.bbg.model.biz.BizUser;
import com.bbg.model.sys.SysMenu;
import com.bbg.model.sys.SysUser;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public interface RedisService extends RedisBase {
    // 管理员登录
    String adminLogin(SysUser user);

    // 管理员登出
    void adminLogout(String token);

    // 获得管理员信息
    SysUser getAdmin(String token);

    // 管理员session延期
    SysUser expireAdmin(String token);

    // 更新管理员权限
    void updateAdminPermission(String token, List<SysMenu> sysMenus);

    // 删除管理员权限
    void deleteAdminPermission(String token);

    // 获得单个权限
    SysMenu getAdminPermission(String token, String path);

    // 用户登录
    String userLogin(BizUser user);

    // 用户登出
    void userLogout(String token);

    // 获得用户
    BizUser getUser(String token);

    // 用户session延期
    boolean expireUser(String token);

    // 更新用户缓存
    void updateUser(BizUser user);
}
