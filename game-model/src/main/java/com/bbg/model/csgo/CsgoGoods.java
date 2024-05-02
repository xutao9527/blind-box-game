package com.bbg.model.csgo;

import com.bbg.model.record.CsgoGoodsRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Schema(description = "Csgo商品表")
public class CsgoGoods extends CsgoGoodsRecord {

}