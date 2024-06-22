package com.bbg.admin;

import com.mybatisflex.spring.boot.FlexTransactionAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@MapperScan("com.bbg.core.mapper")
@EnableFeignClients(basePackages = {"com.bbg.core"})
@ComponentScan(basePackages = {"com.bbg.core","com.bbg.admin"})
@SpringBootApplication(exclude = FlexTransactionAutoConfiguration.class)
public class AdminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }
}
