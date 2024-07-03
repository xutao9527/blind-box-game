package com.bbg.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
// @EnableFeignClients(basePackages = {"com.bbg.gateway.feign"})
public class GatewayServerApp {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApp.class,args);
    }

}