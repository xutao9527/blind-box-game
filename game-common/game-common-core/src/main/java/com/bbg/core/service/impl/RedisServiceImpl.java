package com.bbg.core.service.impl;

import com.bbg.core.service.RedisService;
import com.bbg.core.service.base.RedisBaseImpl;
import com.bbg.model.biz.BizUser;
import com.bbg.model.sys.SysUser;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class RedisServiceImpl extends RedisBaseImpl implements RedisService {

    public String adminLogin(SysUser user) {
        String adminUserId = "admin::token::uid::" + user.getId();
        String newToken = UUID.randomUUID().toString();
        String adminUserToken = "admin::info::token::" + newToken;
        String oldToken = (String) get(adminUserId);
        if (oldToken != null) {
            delete(oldToken);
        }
        set(adminUserId, adminUserToken, 3600L);
        set(adminUserToken, user, 3600L);
        return newToken;
    }

    public void adminLogout(String token) {
        SysUser user = getAdmin(token);
        if (user != null) {
            delete("admin::token::uid::" + user.getId());
            delete("admin::info::token::" + token);
        }
    }

    public SysUser getAdmin(String token) {
        String adminUserToken = "admin::info::token::" + token;
        return (SysUser) get(adminUserToken);
    }

    public String userLogin(BizUser user) {
        String userId = "user::token::uid::" + user.getId();
        String newToken = UUID.randomUUID().toString();
        String userToken = "user::info::token::" + newToken;
        String oldToken = (String) get(userId);
        if (oldToken != null) {
            delete(oldToken);
        }
        set(userId, userToken, 3600L);
        set(userToken, user, 3600L);
        return newToken;
    }

    public void userLogout(String token) {
        SysUser user = getAdmin(token);
        if (user != null) {
            delete("user::token::uid::" + user.getId());
            delete("user::info::token::" + token);
        }
    }

    public BizUser getUser(String token) {
        String bizUserToken = "user::info::token::" + token;
        return (BizUser) get(bizUserToken);
    }
}
