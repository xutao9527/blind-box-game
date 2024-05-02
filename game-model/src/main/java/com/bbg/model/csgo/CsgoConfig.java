package com.bbg.model.csgo;

import com.bbg.model.record.CsgoConfigRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Schema(description = "游戏配置")
public class CsgoConfig extends CsgoConfigRecord {

}