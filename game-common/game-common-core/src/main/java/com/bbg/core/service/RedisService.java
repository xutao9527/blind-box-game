package com.bbg.core.service;

import com.bbg.core.service.base.RedisBase;
import com.bbg.model.biz.BizUser;
import com.bbg.model.sys.SysUser;

public interface RedisService extends RedisBase {

    String adminLogin(SysUser user);

    void adminLogout(String token);

    SysUser getAdmin(String token);

    String userLogin(BizUser user);

    void userLogout(String token);

    BizUser getUser(String token);

}
