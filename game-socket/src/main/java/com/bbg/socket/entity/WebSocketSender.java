package com.bbg.socket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.FluxSink;

@Data
@AllArgsConstructor
public class WebSocketSender {
    private WebSocketSession session;
    private FluxSink<WebSocketMessage> sink;

    public void send(String data) {
        sink.next(session.textMessage(data));
    }
}
