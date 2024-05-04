package com.bbg.model.csgo;

import com.bbg.model.record.CsgoBoxGoodsRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "CSGO箱子商品")
public class CsgoBoxGoods extends CsgoBoxGoodsRecord {

}