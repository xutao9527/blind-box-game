package com.bbg.admin.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeignConfig {

    @Bean
    public Retryer retryer() {
        // period       重试间隔时间
        // maxPeriod    最大重试间隔时间
        // maxAttempts  最大重试次数
        return new Retryer.Default(
                3000,
                6000,
                3);
    }
}
