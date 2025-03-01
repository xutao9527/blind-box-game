package com.bbg.model.csgo;

import com.bbg.model.record.CsgoStorehouseRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "装备仓库")
public class CsgoStorehouse extends CsgoStorehouseRecord {

}