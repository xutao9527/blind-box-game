package com.bbg.socket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "WebSocket接口")
@RequiredArgsConstructor
public class WebSocketController {

    private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);

    @PostMapping("/send")
    @Operation(summary = "发送消息", description = "发送消息")
    public void sendMessage(@RequestBody String message) {
        log.info("send message: {}", message);
    }

}
