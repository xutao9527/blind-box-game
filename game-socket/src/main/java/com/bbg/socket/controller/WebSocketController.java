package com.bbg.socket.controller;

import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.WebSocketMsg;
import com.bbg.socket.entity.WebSocketSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentMap;


@RestController
@Tag(name = "WebSocket接口")
@RequiredArgsConstructor
@Validated
public class WebSocketController {

    private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);
    public final ConcurrentMap<String, WebSocketSender> adminSenderMap;

    @PostMapping(value = "/send")
    @Operation(summary = "管理后台推送消息", description = "管理后台推送消息")
    public ApiRet<Boolean> sendMessage(@RequestBody WebSocketMsg webSocketMsg) {
        log.info("send message: {}", webSocketMsg.getMessage());
        adminSenderMap.forEach((id, sender) -> sender.send(webSocketMsg.getMessage()));
        return ApiRet.buildOk();
    }

}
