package com.bbg.socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SocketServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocketServiceApplication.class, args);
    }
}