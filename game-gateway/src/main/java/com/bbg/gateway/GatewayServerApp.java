package com.bbg.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayServerApp {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApp.class,args);
    }
}