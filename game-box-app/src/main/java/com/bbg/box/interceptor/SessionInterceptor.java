package com.bbg.box.interceptor;

import com.bbg.core.box.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor

public class SessionInterceptor implements HandlerInterceptor {
    public final RedisService redisService;

    @Autowired
    private DiscoveryClient discoveryClient;



    @Override
    // 在请求处理之前进行拦截逻辑，返回 true 表示继续执行，返回 false 表示终止执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("admin-server");
        System.out.println(serviceInstances);
        String token = request.getHeader("token");
        if (token != null) {
            redisService.expireUser(token);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    // 请求处理之后但在视图渲染之前执行的拦截逻辑
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    // 请求完成后的拦截逻辑，可以用于资源清理等操作
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
