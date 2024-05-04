package com.bbg.core.box.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

public class BoxDto {
    @Data
    @Accessors(chain = true)
    @Schema(description = "获得盲盒列表")
    public static class GetBoxReq implements Serializable {
        /**
         * 箱子类型
         */
        @Schema(description = "箱子类型")
        private String type;
    }
}
