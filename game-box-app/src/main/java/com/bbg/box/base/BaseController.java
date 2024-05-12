package com.bbg.box.base;

import cn.hutool.core.util.StrUtil;
import com.bbg.core.box.service.RedisService;
import com.bbg.model.base.BaseModel;
import com.bbg.model.biz.BizUser;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

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

    public QueryWrapper buildQueryWrapper(QueryWrapper queryWrapper, Object queryEntity) {
        // Entity.expandProps 扩展属性为List并size等2, 进行范围查询
        if (queryEntity instanceof BaseModel && ((BaseModel) queryEntity).getExpandProps() != null) {
            ((BaseModel) queryEntity).getExpandProps().forEach((key, value) -> {
                if (value instanceof List && ((List<?>) value).size() == 2) {
                    QueryCondition queryCondition = QueryMethods.column(StrUtil.toUnderlineCase(key)).between(((List<?>) value).get(0), ((List<?>) value).get(1));
                    queryWrapper.and(queryCondition);
                }
                if(key.equals("orderField") && value instanceof Map<?,?>){
                    ((Map<?, ?>) value).forEach((sortField,sortValue)->{
                        queryWrapper.orderBy(QueryMethods.column(StrUtil.toUnderlineCase((String) sortField)),sortValue.equals("ascending"));
                    });
                }
            });
        }
        return queryWrapper;
    }
}
