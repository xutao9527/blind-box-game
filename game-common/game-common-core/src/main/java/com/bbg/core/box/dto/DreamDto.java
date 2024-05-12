package com.bbg.core.box.dto;

import com.bbg.model.csgo.CsgoBox;
import com.bbg.model.csgo.CsgoBoxGoods;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

public class DreamDto {
    @Data
    @Accessors(chain = true)
    @Schema(description = "获得追梦商品列表")
    public static class DreamReq implements Serializable {
        @Schema(description = "页数")
        private Number pageNumber;
        @Schema(description = "页大小")
        private Number pageSize;
        @Schema(description = "商品名称")
        private String name;
        @Schema(description = "商品类型")
        private String type;
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "获得追梦商品列表结果")
    public static class DreamRes implements Serializable {
        @Schema(description = "追梦商品列表")
        private Page<CsgoBoxGoods> csgoBoxGoodsPage;
    }
}
