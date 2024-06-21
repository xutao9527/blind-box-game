package com.bbg.socket.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class GameWebSocketHandler implements WebSocketHandler {


    private final ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    @NonNull
    public Mono<Void> handle(WebSocketSession session) {
        // 新建连接
        log.info("New session {} has been opened", session.getId());
        sessions.put(session.getId(), session);

        Mono<Void> incoming = session.receive().map(WebSocketMessage::getPayloadAsText)
                .doOnNext(System.out::println).then();
        Mono<Void> outgoing = session.send(Flux.interval(Duration.ofSeconds(1))
                .map(String::valueOf)
                .map(session::textMessage)
        );

        return Mono.when(outgoing, incoming).then().doFinally(signalType -> {
            sessions.remove(session.getId());
            log.info("Session {} has been closed", session.getId());
        });
    }
}
