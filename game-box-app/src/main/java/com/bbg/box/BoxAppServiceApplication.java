package com.bbg.box;

import com.mybatisflex.spring.boot.FlexTransactionAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = FlexTransactionAutoConfiguration.class)
@ComponentScan(basePackages = {"com.bbg.core", "com.bbg.box"})
public class BoxAppServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoxAppServiceApplication.class, args);
    }
}