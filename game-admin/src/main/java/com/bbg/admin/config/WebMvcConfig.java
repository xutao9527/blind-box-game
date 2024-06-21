package com.bbg.admin.config;

import com.bbg.admin.interceptor.SessionInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    public final SessionInterceptor sessionInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/doc")                                // 在线接口文档
                .excludePathPatterns("/v3/api-docs")                           // 在线接口文档
                .excludePathPatterns("/swagger-ui/**")                      // 在线接口文档
                .excludePathPatterns("/api-docs/swagger-config")            // 在线接口文档
                .excludePathPatterns("/sysUser/login");
    }
}
