package com.bbg.box.base;

import com.bbg.core.box.service.RedisService;
import com.bbg.model.biz.BizUser;
import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController<T, S extends IService<T>> {
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired(required = false)
    protected RedisService redisService;

    public BizUser getCurrentUser(){
        String token =  request.getHeader("token");
        return redisService.getUser(token);
    }

}
