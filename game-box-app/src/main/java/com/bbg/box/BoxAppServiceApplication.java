package com.bbg.box;

import com.mybatisflex.spring.boot.FlexTransactionAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ImportAutoConfiguration
@MapperScan("com.bbg.box.mapper")
@ComponentScan(basePackages = {"com.bbg.core", "com.bbg.box"})
public class BoxAppServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoxAppServiceApplication.class, args);
    }
}