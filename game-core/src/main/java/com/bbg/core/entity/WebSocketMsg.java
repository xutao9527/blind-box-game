package com.bbg.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "WebSocket消息")
public class WebSocketMsg {
    @Schema(description = "消息内容")
    @NotNull(message = "消息内容不能为空")
    private String msgContent;
}