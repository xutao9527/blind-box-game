package com.bbg.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "消息体")
public class WebSocketMsg {
    @Schema(description = "租户编号")
    private Long tenantId;

    @Schema(description = "消息内容")
    @NotNull(message = "消息内容不能为空")
    @Size(min = 1, message = "消息内容长度必须大于或等于1")
    private String message;
}