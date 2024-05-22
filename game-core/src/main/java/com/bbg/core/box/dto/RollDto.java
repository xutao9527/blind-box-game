package com.bbg.core.box.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

public class RollDto {
    @Data
    @Accessors(chain = true)
    @Schema(description = "获得撸房列表")
    public static class GetRollListReq implements Serializable {
        @Schema(description = "页数")
        private int pageNumber = 1;
        @Schema(description = "页大小")
        private int pageSize = 10;
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "获得撸房用户列表")
    public static class GetRollUsersReq implements Serializable {
        @NotNull
        @Schema(description = "撸房编号")
        private long rollId;
        @Schema(description = "页数")
        private int pageNumber = 1;
        @Schema(description = "页大小")
        private int pageSize = 10;
    }
}
