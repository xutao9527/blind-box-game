package com.bbg.core.feign.socket;

import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.WebSocketMsg;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "socket-server")
public interface WebSocketService {
    @PostMapping(value = "admin/send")
    @Operation(summary = "后台推送消息", description = "管理后台推送消息")
    ApiRet<Boolean> sendAdminMessage(@RequestBody WebSocketMsg webSocketMsg);
}
