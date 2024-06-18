package com.bbg.third.base;

import com.bbg.core.service.RedisService;
import com.bbg.model.biz.BizUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
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
