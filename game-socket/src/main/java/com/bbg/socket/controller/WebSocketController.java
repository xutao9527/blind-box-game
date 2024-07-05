package com.bbg.socket.controller;

import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.WebSocketMsg;
import com.bbg.socket.entity.WebSocketSender;
import com.bbg.socket.handler.BoxWebSocketHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentMap;

@Slf4j
@Validated
@RestController
@Tag(name = "WebSocket接口")
@RequiredArgsConstructor
public class WebSocketController {

    private final ConcurrentMap<String, WebSocketSender> adminSenderMap;
    private final ConcurrentMap<Long, ConcurrentMap<String, WebSocketSender>> boxTenantSenderMap;

    @PostMapping(value = "admin/send")
    @Operation(summary = "后台推送消息", description = "管理后台推送消息")
    public ApiRet<Boolean> sendAdminMessage(@RequestBody WebSocketMsg webSocketMsg) {
        adminSenderMap.forEach((id, sender) -> sender.send(webSocketMsg.getMessage()));
        return ApiRet.buildOk();
    }

    @PostMapping(value = "box/send")
    @Operation(summary = "游戏推送消息", description = "游戏推送消息")
    public ApiRet<Boolean> sendGameMessage(@RequestBody WebSocketMsg webSocketMsg) {
        log.info("sendGameMessage:{}", webSocketMsg);
        if(webSocketMsg.getTenantId() == null) {
            return ApiRet.buildNo("tenantId is Invalid");
        }
        if(boxTenantSenderMap.get(webSocketMsg.getTenantId())!=null){
            boxTenantSenderMap.get(webSocketMsg.getTenantId())
                    .forEach((id, sender) -> sender.send(webSocketMsg.getMessage()));
        }
        return ApiRet.buildOk();
    }
}
