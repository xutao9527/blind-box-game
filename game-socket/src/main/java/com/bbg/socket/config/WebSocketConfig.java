package com.bbg.socket.config;

import com.bbg.socket.annotation.CustomSimpleUrlHandlerMapping;
import com.bbg.socket.entity.WebSocketSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

@EnableWebFlux
@Configuration
public class WebSocketConfig {

    @Bean
    public ConcurrentMap<String, WebSocketSender> adminSenderMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean ConcurrentMap<String, WebSocketSender> gameAllSenderMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ConcurrentMap<String, ConcurrentMap<String, WebSocketSender>> gameTenantSenderMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public HandlerMapping webSocketMapping() {
        return new CustomSimpleUrlHandlerMapping();
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter(webSocketService());
    }

    @Bean
    public WebSocketService webSocketService() {
        return new HandshakeWebSocketService(new ReactorNettyRequestUpgradeStrategy());
    }

}
