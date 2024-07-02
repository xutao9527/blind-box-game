package com.bbg.admin.interceptor;

import com.alibaba.fastjson.JSON;
import com.bbg.core.service.RedisService;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.service.sys.SysTenantService;
import com.bbg.model.sys.SysMenu;
import com.bbg.model.sys.SysTenant;
import com.bbg.model.sys.SysUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.lang.annotation.Annotation;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {

    public final RedisService redisService;
    public final SysTenantService sysTenantService;

    @Override
    // 在请求处理之前进行拦截逻辑，返回 true 表示继续执行，返回 false 表示终止执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String matchingUrl = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        if (token != null) {
            SysUser sysUser = redisService.expireAdmin(token);
            if (sysUser == null) {
                noLogin(response);
                return false;
            }
            // 获得用户的租户信息
            SysTenant sysTenant = sysTenantService.getById(sysUser.getTenantId());
            if (sysTenant != null) {
                request.setAttribute("tenantId", sysTenant.getId());
                request.setAttribute("isSuperTenant", sysUser.isSuperTenant());
            } else {
                noTenant(response);
                return false;
            }
            // 判断是否有权限
            SysMenu sysMenu = redisService.getAdminPermission(token, matchingUrl);
            if (sysMenu == null) {
                noPermission(response);
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

    public void noTenant(HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(JSON.toJSON(ApiRet.buildNo("不存在租户")));
    }

    public void noPermission(HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(JSON.toJSON(ApiRet.buildNo("没有接口权限")));
    }
}
