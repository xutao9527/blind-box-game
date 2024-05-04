package com.bbg.core.service;

import com.bbg.core.service.base.RedisBase;
import com.bbg.model.biz.BizUser;
import com.bbg.model.sys.SysUser;

public interface RedisService extends RedisBase {
    // 管理员登录
    String adminLogin(SysUser user);

    // 管理员登出
    void adminLogout(String token);

    // 获得管理员信息
    SysUser getAdmin(String token);

    // 管理员session延期
    void expireAdmin(String token);

    // 用户登录
    String userLogin(BizUser user);

    // 用户登出
    void userLogout(String token);

    // 获得用户
    BizUser getUser(String token);

    // 用户session延期
    public void expireUser(String token);

}
