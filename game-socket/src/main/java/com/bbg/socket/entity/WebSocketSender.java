package com.bbg.socket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.FluxSink;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WebSocketSender {
    private Long tenantId;
    private String tenantCode;
    private WebSocketSession session;
    private FluxSink<WebSocketMessage> sink;

    public void send(String data) {
        sink.next(session.textMessage(data));
    }
}
