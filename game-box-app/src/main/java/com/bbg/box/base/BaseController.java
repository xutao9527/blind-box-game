package com.bbg.box.base;

import cn.hutool.core.util.StrUtil;
import com.bbg.core.service.RedisService;
import com.bbg.model.base.BaseModel;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseController<T, S extends IService<T>> {
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired(required = false)
    protected RedisService redisService;


}
