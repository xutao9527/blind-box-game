package com.bbg.core.box.service.impl;

import com.bbg.core.box.service.RedisService;
import com.bbg.core.base.RedisBaseImpl;
import com.bbg.model.biz.BizUser;
import com.bbg.model.sys.SysUser;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
        set(adminUserId, adminUserToken, 3600L+3, TimeUnit.SECONDS);
        set(adminUserToken, user, 3600L, TimeUnit.SECONDS);
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

    public void expireAdmin(String token){
        String adminUserToken = "admin::info::token::" + token;
        SysUser sysUser = getAdmin(token);
        if(sysUser!=null){
            String adminUserId = "admin::token::uid::" + sysUser.getId();
            expire(adminUserId, 3600L+3, TimeUnit.SECONDS);
            expire(adminUserToken, 3600L, TimeUnit.SECONDS);
        }
    }

    public String userLogin(BizUser user) {
        String userId = "user::token::uid::" + user.getId();
        String newToken = UUID.randomUUID().toString();
        String userToken = "user::info::token::" + newToken;
        String oldToken = (String) get(userId);
        if (oldToken != null) {
            delete(oldToken);
        }
        set(userId, userToken, 3600L+3, TimeUnit.SECONDS);
        set(userToken, user, 3600L, TimeUnit.SECONDS);
        return newToken;
    }

    public void userLogout(String token) {
        BizUser user = getUser(token);
        if (user != null) {
            delete("user::token::uid::" + user.getId());
            delete("user::info::token::" + token);
        }
    }

    public BizUser getUser(String token) {
        String bizUserToken = "user::info::token::" + token;
        return (BizUser) get(bizUserToken);
    }

    public void expireUser(String token){
        String userToken = "user::info::token::" + token;
        BizUser bizUser = getUser(token);
        if(bizUser!=null){
            String userId = "user::token::uid::" + bizUser.getId();
            expire(userId, 3600L+3, TimeUnit.SECONDS);
            expire(userToken, 3600L, TimeUnit.SECONDS);
        }
    }

    public void updateUser(BizUser user){
        String userId = "user::token::uid::" + user.getId();
        String oldToken = (String) get(userId);
        if(oldToken!=null){
            set(userId, oldToken, 3600L+3, TimeUnit.SECONDS);
            set(oldToken, user, 3600L, TimeUnit.SECONDS);
        }
    }
}
