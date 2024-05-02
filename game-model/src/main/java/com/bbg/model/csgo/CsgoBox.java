package com.bbg.model.csgo;

import com.bbg.model.record.CsgoBoxRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Schema(description = "CSGO箱子")
public class CsgoBox extends CsgoBoxRecord {

}