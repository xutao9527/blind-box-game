package com.bbg.model.csgo;

import com.bbg.model.record.CsgoRollRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "撸房")
public class CsgoRoll extends CsgoRollRecord {

    @Schema(description = "撸房装备列表")
    List<CsgoRollGood> rollGoods  = new ArrayList<>();

    @Schema(description = "撸房用户列表")
    List<CsgoRollUser> rollUsers  = new ArrayList<>();

}