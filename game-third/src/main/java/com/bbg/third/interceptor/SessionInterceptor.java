package com.bbg.third.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    // 在请求处理之前进行拦截逻辑，返回 true 表示继续执行，返回 false 表示终止执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求的 URI
        String uri = request.getRequestURI();
        // 获取查询参数
        String queryString = request.getQueryString();
        // 构建完整的 URL
        String fullUrl = uri + (queryString != null ? "?" + queryString : "");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
