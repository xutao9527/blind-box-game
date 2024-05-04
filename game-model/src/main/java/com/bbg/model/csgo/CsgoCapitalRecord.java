package com.bbg.model.csgo;

import com.bbg.model.record.CsgoCapitalRecordRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "资金流水")
public class CsgoCapitalRecord extends CsgoCapitalRecordRecord {

}