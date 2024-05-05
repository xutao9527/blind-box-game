package com.bbg.core.box.dto;

import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoBox;
import com.bbg.model.csgo.CsgoStorehouse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

public class BoxDto {
    @Data
    @Accessors(chain = true)
    @Schema(description = "获得盲盒列表")
    public static class GetBoxReq implements Serializable {
        @Schema(description = "箱子类型")
        private String type;
    }


    @Data
    @Accessors(chain = true)
    @Schema(description = "获得盲盒列表结果")
    public static class GetBoxRes implements Serializable {
        @Schema(description = "盲盒列表")
        private List<CsgoBox> csgoBoxes;
    }


    @Data
    @Accessors(chain = true)
    @Schema(description = "打开盲盒")
    public static class OpenBoxReq implements Serializable {
        @Schema(description = "箱子类型")
        private Long boxId;
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "打开盲盒结果")
    public static class OpenBoxRes implements Serializable {
        @Schema(description = "新用户信息")
        BizUser bizUser;
        @Schema(description = "获得的装备")
        CsgoStorehouse luckStorehouse;
    }
}
