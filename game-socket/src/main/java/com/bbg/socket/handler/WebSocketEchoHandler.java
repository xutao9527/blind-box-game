package com.bbg.socket.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEchoHandler implements WebSocketHandler {

    public final ConcurrentMap<String, WebSocketSender> senderMap;

    @Override
    @NonNull
    public Mono<Void> handle(WebSocketSession session) {
        Mono<Void> incoming = session.receive().map(WebSocketMessage::getPayloadAsText)
                .doOnNext(log::info).then();
        Mono<Void> outgoing = session.send(
                Flux.create(sink -> {
                    log.info("session opened: {}", session.getId());
                    senderMap.put(session.getId(), new WebSocketSender(session, sink));
                })
        );
        return Mono.when(outgoing, incoming).then().doFinally(signalType -> {
            log.info("session closed: {}", session.getId());
            senderMap.remove(session.getId());
        });
    }
}
