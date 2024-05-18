package com.bbg.core.box.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

public class RollDto {
    @Data
    @Accessors(chain = true)
    @Schema(description = "获得对战房间列表")
    public static class GetRollListReq implements Serializable {
        @Schema(description = "页数")
        private int pageNumber = 1;
        @Schema(description = "页大小")
        private int pageSize = 10;
    }
}
