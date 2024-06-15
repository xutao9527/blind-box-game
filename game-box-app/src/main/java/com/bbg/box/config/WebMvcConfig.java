package com.bbg.box.config;


import com.bbg.box.interceptor.SessionInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class  WebMvcConfig implements WebMvcConfigurer {
    public final SessionInterceptor sessionInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/csgoRobot/list")                     // 机器人
                .excludePathPatterns("/csgoRoll/getRollList")               // 获取撸房列表
                .excludePathPatterns("/csgoRoll/getRoll")                   // 获得撸房
                .excludePathPatterns("/csgoBox/list")                       // 获得盲盒列表
                .excludePathPatterns("/csgoBox/dreamList")                  // 获得追梦商品列表
                .excludePathPatterns("/csgoBattleRoom/getRoomList")         // 获取对战房间列表
                .excludePathPatterns("/csgoBattleRoom/getRoom")             // 获得对战房间信息
                .excludePathPatterns("/csgoBattleRoom/joinByUserId")        // 测试接口,后面屏蔽
                .excludePathPatterns("/doc")                                // 在线接口文档
                .excludePathPatterns("/api-docs")                           // 在线接口文档
                .excludePathPatterns("/swagger-ui/**")                      // 在线接口文档
                .excludePathPatterns("/api-docs/swagger-config")            // 在线接口文档
                .excludePathPatterns("/bizUser/register")                   // 注册
                .excludePathPatterns("/bizUser/loginByPwd")                 // 密码登录
                .excludePathPatterns("/bizUser/loginByCode")                // 验证码登录
                .excludePathPatterns("/bizUser/sendCode");                  // 验证码
    }
}
