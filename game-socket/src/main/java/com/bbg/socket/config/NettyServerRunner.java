package com.bbg.socket.config;


import com.bbg.socket.netty.WebSocketNettyServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class NettyServerRunner implements ApplicationRunner {

    public final WebSocketNettyServer webSocketNettyServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 异步启动netty服务
        new Thread(webSocketNettyServer::startNettyServer).start();
    }
}
