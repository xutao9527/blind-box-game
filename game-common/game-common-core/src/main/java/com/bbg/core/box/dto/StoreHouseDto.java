package com.bbg.core.box.dto;

import com.bbg.model.csgo.CsgoStorehouse;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

public class StoreHouseDto {

    @Data
    @Accessors(chain = true)
    @Schema(description = "获得背包列表")
    public static class StoreHouseReq implements Serializable {
        @Schema(description = "页数")
        private Number pageNumber;
        @Schema(description = "页大小")
        private Number pageSize;

    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "获得背包列表结果")
    public static class StoreHouseRes implements Serializable {
        @Schema(description = "背包装备列表")
        private Page<CsgoStorehouse> storehousePage;
    }
}
