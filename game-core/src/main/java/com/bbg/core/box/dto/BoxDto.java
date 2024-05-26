package com.bbg.core.box.dto;

import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoBox;
import com.bbg.model.csgo.CsgoOpenBoxLog;
import com.bbg.model.csgo.CsgoStorehouse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class BoxDto {
    @Data
    @Accessors(chain = true)
    @Schema(description = "获得盲盒列表参数")
    public static class GetBoxReq implements Serializable {
        @NotNull(message = "箱子类型不能为空")
        @Schema(description = "箱子类型:1=普通盲盒,2=对战盲盒")
        @Pattern(regexp = "[1,2]",message = "箱子类型:1=普通盲盒,2=对战盲盒,3=撸房盲盒")
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
    @Schema(description = "打开盲盒参数")
    public static class OpenBoxReq implements Serializable {
        @Schema(description = "箱子编号")
        private Long boxId;
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "打开盲盒结果")
    public static class OpenBoxRes implements Serializable {
        @Schema(description = "余额")
        private BigDecimal money;
        @Schema(description = "获得的装备")
        CsgoOpenBoxLog csgoOpenBoxLog;
    }
}
