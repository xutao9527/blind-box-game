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
import org.springframework.web.reactive.socket.HandshakeInfo;
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

    private final WebClient.Builder webClientBuilder;

    private final ConcurrentMap<String, WebSocketSender> boxSenderMap ;

    private final ConcurrentMap<String, ConcurrentMap<String, WebSocketSender>> boxTenantSenderMap;

    @Override
    @NonNull
    public Mono<Void> handle(WebSocketSession session) {
        HandshakeInfo handshakeInfo = session.getHandshakeInfo();
        MultiMap<String> queryParams = UrlEncoded.decodeQuery(handshakeInfo.getUri().getRawQuery());
        String tenantCode = queryParams.getString("t_code");
        //进行http请求，根据tenantCode查询tenantId

        getTenantId(tenantCode);

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

    public void getTenantId(String tenantCode) {
        String url = "http://admin-server/sysTenant/getTenantId?tenantCode=" + tenantCode;
         webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(Long.class)
                .log().subscribe();
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
