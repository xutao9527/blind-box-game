package com.bbg.box;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ImportAutoConfiguration
@MapperScan("com.bbg.core.mapper")
@EnableFeignClients(basePackages = {"com.bbg.core"})
@ComponentScan(basePackages = {"com.bbg.core", "com.bbg.box"})
@EnableTransactionManagement
public class BoxAppServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoxAppServiceApplication.class, args);
    }
}