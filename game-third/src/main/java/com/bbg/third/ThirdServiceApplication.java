package com.bbg.third;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.bbg.core.mapper")
@EnableFeignClients(basePackages = {"com.bbg.core"})
@ComponentScan(basePackages = {"com.bbg.core", "com.bbg.third"})
@EnableTransactionManagement
@ServletComponentScan
public class ThirdServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThirdServiceApplication.class, args);
	}

}
