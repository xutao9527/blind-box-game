package com.bbg.box.interceptor;

import com.alibaba.fastjson.JSON;
import com.bbg.core.service.RedisService;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.service.biz.BizChannelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {

    public final RedisService redisService;

    @Override
    // 在请求处理之前进行拦截逻辑，返回 true 表示继续执行，返回 false 表示终止执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求的 URI
        String uri = request.getRequestURI();
        // 获取查询参数
        String queryString = request.getQueryString();
        // 构建完整的 URL
        String fullUrl = uri + (queryString != null ? "?" + queryString : "");
        log.info("请求的URL为：{}", fullUrl);
        String token = request.getHeader("token");
        if (token != null) {
            if (!redisService.expireUser(token)) {
                noLogin(response);
                return false;
            }
        } else {
            noLogin(response);
            return false;
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

    public void noLogin(HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(JSON.toJSON(ApiRet.buildNo("用户没有登录")));
    }
}
