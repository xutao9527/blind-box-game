package com.bbg.core.service.impl;

import com.bbg.core.base.RedisBaseImpl;
import com.bbg.core.service.RedisService;
import com.bbg.core.constrans.KeyConst;
import com.bbg.model.biz.BizUser;
import com.bbg.model.sys.SysUser;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl extends RedisBaseImpl implements RedisService {

    public final long userLiveTime = 60 * 60 * 24;
    public final long adminLiveTime = 60 * 60 * 24;

    public String adminLogin(SysUser user) {
        String adminUserId = KeyConst.build(KeyConst.ADMIN_TOKEN_UID, user.getId().toString());
        String newToken = UUID.randomUUID().toString();
        String adminUserToken = KeyConst.build(KeyConst.ADMIN_INFO_TOKEN, newToken);
        String oldToken = (String) get(adminUserId);
        if (oldToken != null) {
            delete(oldToken);
        }
        set(adminUserId, adminUserToken, adminLiveTime + 3, TimeUnit.SECONDS);
        set(adminUserToken, user, adminLiveTime, TimeUnit.SECONDS);
        return newToken;
    }

    public void adminLogout(String token) {
        SysUser user = getAdmin(token);
        if (user != null) {
            delete(KeyConst.build(KeyConst.ADMIN_TOKEN_UID, user.getId().toString()));
            delete(KeyConst.build(KeyConst.ADMIN_INFO_TOKEN, token));
        }
    }

    public SysUser getAdmin(String token) {
        String adminUserToken = KeyConst.build(KeyConst.ADMIN_INFO_TOKEN, token);
        return (SysUser) get(adminUserToken);
    }

    public SysUser expireAdmin(String token) {
        String adminUserToken = KeyConst.build(KeyConst.ADMIN_INFO_TOKEN, token);
        SysUser sysUser = getAdmin(token);
        if (sysUser != null) {
            String adminUserId = KeyConst.build(KeyConst.ADMIN_TOKEN_UID, sysUser.getId().toString());
            expire(adminUserId, adminLiveTime + 3, TimeUnit.SECONDS);
            expire(adminUserToken, adminLiveTime, TimeUnit.SECONDS);
        }
        return sysUser;
    }

    public String userLogin(BizUser user) {
        String userId = KeyConst.build(KeyConst.USER_TOKEN_UID, user.getId().toString());
        String newToken = UUID.randomUUID().toString();
        String userToken = KeyConst.build(KeyConst.USER_INFO_TOKEN, newToken);
        String oldToken = (String) get(userId);
        if (oldToken != null) {
            delete(oldToken);
        }
        set(userId, userToken, userLiveTime + 3, TimeUnit.SECONDS);
        set(userToken, user, userLiveTime, TimeUnit.SECONDS);
        return newToken;
    }

    public void userLogout(String token) {
        BizUser user = getUser(token);
        if (user != null) {
            delete(KeyConst.build(KeyConst.USER_TOKEN_UID, user.getId().toString()));
            delete(KeyConst.build(KeyConst.USER_INFO_TOKEN, token));
        }
    }

    public BizUser getUser(String token) {
        String bizUserToken = KeyConst.build(KeyConst.USER_INFO_TOKEN, token);
        return (BizUser) get(bizUserToken);
    }

    public boolean expireUser(String token) {
        String userToken = KeyConst.build(KeyConst.USER_INFO_TOKEN, token);
        BizUser bizUser = getUser(token);
        if (bizUser != null) {
            String userId = KeyConst.build(KeyConst.USER_TOKEN_UID, bizUser.getId().toString());
            expire(userId, userLiveTime + 3, TimeUnit.SECONDS);
            expire(userToken, userLiveTime, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    public void updateUser(BizUser user) {
        String userId = KeyConst.build(KeyConst.USER_TOKEN_UID, user.getId().toString());
        String oldToken = (String) get(userId);
        if (oldToken != null) {
            set(userId, oldToken, userLiveTime + 3, TimeUnit.SECONDS);
            set(oldToken, user, userLiveTime, TimeUnit.SECONDS);
        }
    }
}
