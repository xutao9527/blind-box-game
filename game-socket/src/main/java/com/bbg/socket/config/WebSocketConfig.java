package com.bbg.socket.config;

import com.bbg.socket.annotation.CustomSimpleUrlHandlerMapping;
import com.bbg.socket.handler.AdminWebSocketHandler;
import com.bbg.socket.entity.WebSocketSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@EnableWebFlux
@Configuration
public class WebSocketConfig {
    @Lazy
    @Autowired
    AdminWebSocketHandler adminWebSocketHandler;

    @Bean
    public ConcurrentMap<String, WebSocketSender> senderMap() {
        return new ConcurrentHashMap<>();
    }

    // @Bean
    // public HandlerMapping handlerMapping() {
    //     Map<String, WebSocketHandler> map = new HashMap<>();
    //     // ws://localhost:7788/echo
    //     map.put("/", adminWebSocketHandler);
    //     SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
    //     mapping.setUrlMap(map);
    //     mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
    //     return webSocketMapping();
    // }

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
