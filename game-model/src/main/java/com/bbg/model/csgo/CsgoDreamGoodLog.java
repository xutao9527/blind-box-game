package com.bbg.model.csgo;

import com.bbg.model.record.CsgoDreamGoodLogRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "CSGO追梦日志")
public class CsgoDreamGoodLog extends CsgoDreamGoodLogRecord {

}