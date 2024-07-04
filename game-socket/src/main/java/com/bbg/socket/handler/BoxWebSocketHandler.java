package com.bbg.socket.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class BoxWebSocketHandler implements WebSocketHandler {

    @Override
    @NonNull
    public Mono<Void> handle(WebSocketSession session) {
        return null;
    }
}
