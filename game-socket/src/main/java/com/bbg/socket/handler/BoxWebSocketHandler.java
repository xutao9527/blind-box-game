package com.bbg.socket.handler;

import com.bbg.socket.annotation.WebSocketMapping;
import com.bbg.socket.entity.WebSocketSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Component
@RequiredArgsConstructor
@WebSocketMapping("/box")
public class BoxWebSocketHandler implements WebSocketHandler {

    public final static ConcurrentMap<String, Long> codeTenantIdMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, WebSocketSender> boxSenderMap;
    private final ConcurrentMap<Long, ConcurrentMap<String, WebSocketSender>> boxTenantSenderMap;

    @Override
    @NonNull
    public Mono<Void> handle(WebSocketSession session) {
        HandshakeInfo handshakeInfo = session.getHandshakeInfo();
        MultiMap<String> queryParams = UrlEncoded.decodeQuery(handshakeInfo.getUri().getRawQuery());
        String tenantCode = queryParams.getString("t_code");
        if (tenantCode == null || codeTenantIdMap.get(tenantCode) == null) {
            WebSocketMessage closeMessage = session.textMessage("t_code is Invalid");
            return session.send(Mono.just(closeMessage)).then(session.close());
        }
        Mono<Void> incoming = session.receive().map(WebSocketMessage::getPayloadAsText)
                .doOnNext(log::info).then();
        Mono<Void> outgoing = session.send(
                Flux.create(sink -> {
                    addConnection(session.getId(), new WebSocketSender()
                            .setTenantCode(tenantCode)
                            .setTenantId(codeTenantIdMap.get(tenantCode))
                            .setSession(session)
                            .setSink(sink));
                })
        );
        return Mono.when(outgoing, incoming).then().doFinally(signalType -> {
            removeConnection(session.getId());
        });
    }

    // 添加连接
    public void addConnection(String sessionId, WebSocketSender sender) {
        boxSenderMap.put(sessionId, sender);
        boxTenantSenderMap.computeIfAbsent(sender.getTenantId(), k -> new ConcurrentHashMap<>()).put(sessionId, sender);
    }

    // 移除连接
    public void removeConnection(String sessionId) {
        WebSocketSender sender = boxSenderMap.remove(sessionId);
        if(sender != null) {
            boxTenantSenderMap.get(sender.getTenantId()).remove(sessionId);
        }

    }
}
