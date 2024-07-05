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
@WebSocketMapping("/box")
public class BoxWebSocketHandler implements WebSocketHandler {
    // 存储 sessionId 和 WebSocketSender 的映射
    private final ConcurrentMap<String, WebSocketSender> boxSenderMap ;
    // 存储 tenantId 和 WebSocketSender 列表的映射
    private final ConcurrentMap<String, ConcurrentMap<String, WebSocketSender>> boxTenantSenderMap;

    @Override
    @NonNull
    public Mono<Void> handle(WebSocketSession session) {
        String queryMap = session.getHandshakeInfo().getUri().getQuery();
        Mono<Void> incoming = session.receive().map(WebSocketMessage::getPayloadAsText)
                .doOnNext(log::info).then();
        Mono<Void> outgoing = session.send(
                Flux.create(sink -> {
                    addConnection(session.getId(), new WebSocketSender().setSession(session).setSink(sink));
                })
        );
        return Mono.when(outgoing, incoming).then().doFinally(signalType -> {
            removeConnection(session.getId());
        });
    }

    // 添加连接
    public void addConnection(String sessionId, WebSocketSender sender) {
        // 将 sender 添加到 sessionIdSenderMap
        boxSenderMap.put(sessionId, sender);
        // 将 sessionId 和 tenantId 的映射存储起来
        // sessionIdTenantIdMap.put(sessionId, tenantId);
        // 将 sender 添加到 tenantId 对应的列表中
        // tenantIdSenderMap.computeIfAbsent(tenantId, key -> new ConcurrentLinkedQueue<>()).add(sender);
    }

    // 移除连接
    public void removeConnection(String sessionId) {
        // 从 sessionIdSenderMap 中移除 sessionId 对应的 sender
        WebSocketSender sender = boxSenderMap.remove(sessionId);
        // if (sender != null) {
        //     // 如果存在对应的 sender，从 tenantIdSenderMap 中移除
        //     String tenantId = sessionIdTenantIdMap.remove(sessionId);
        //     if (tenantId != null) {
        //         ConcurrentLinkedQueue<WebSocketSender> senderList = tenantIdSenderMap.get(tenantId);
        //         if (senderList != null) {
        //             senderList.remove(sender);
        //             // 如果列表为空，则移除 tenantId 的映射
        //             if (senderList.isEmpty()) {
        //                 tenantIdSenderMap.remove(tenantId);
        //             }
        //         }
        //     }
        // }
    }
}
