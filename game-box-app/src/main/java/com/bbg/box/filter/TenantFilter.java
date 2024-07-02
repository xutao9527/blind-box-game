package com.bbg.box.filter;

import com.bbg.core.service.sys.SysTenantService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
@Component
public class TenantFilter implements Filter {
    @Autowired
    SysTenantService sysTenantService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String tenantCode;
        Long tenantId = null;
        if(request instanceof HttpServletRequest httpServletRequest){
            tenantCode = httpServletRequest.getHeader("t_code");
            if(!tenantCode.isEmpty()){
                tenantId = sysTenantService.getTenantId(tenantCode);
            }
        }
        if(tenantId!=null){
            request.setAttribute("tenantId", tenantId);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
