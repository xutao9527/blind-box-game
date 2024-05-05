package com.bbg.model.csgo;

import com.bbg.model.record.CsgoBoxGoodsRecord;

import com.mybatisflex.annotation.Column;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "CSGO箱子商品")
public class CsgoBoxGoods extends CsgoBoxGoodsRecord {

    @Schema(description = "roll点开始区间")
    @Column(ignore = true)
    BigDecimal startRoundNumber;

    @Schema(description = "roll点结束区间")
    @Column(ignore = true)
    BigDecimal endRoundNumber;
}