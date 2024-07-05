package com.bbg.socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
public class SocketServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocketServiceApplication.class, args);
    }
}