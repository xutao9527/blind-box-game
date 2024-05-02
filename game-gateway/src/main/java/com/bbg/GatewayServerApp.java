package com.bbg;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class GatewayServerApp {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(GatewayServerApp.class,args);
    }
}