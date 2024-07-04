package com.bbg.socket.handler;

import com.bbg.socket.annotation.WebSocketMapping;
import com.bbg.socket.entity.WebSocketSender;
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
@WebSocketMapping("/admin")
public class AdminWebSocketHandler implements WebSocketHandler {

    public final ConcurrentMap<String, WebSocketSender> adminSenderMap;

    @Override
    @NonNull
    public Mono<Void> handle(WebSocketSession session) {
        Mono<Void> incoming = session.receive().map(WebSocketMessage::getPayloadAsText)
                .doOnNext(log::info).then();
        Mono<Void> outgoing = session.send(
                Flux.create(sink -> {
                    adminSenderMap.put(session.getId(), new WebSocketSender(session, sink));
                })
        );
        return Mono.when(outgoing, incoming).then().doFinally(signalType -> {
            adminSenderMap.remove(session.getId());
        });
    }
}
