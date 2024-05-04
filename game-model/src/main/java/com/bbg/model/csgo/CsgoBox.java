package com.bbg.model.csgo;

import com.bbg.model.record.CsgoBoxRecord;

import com.mybatisflex.annotation.RelationOneToMany;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "CSGO箱子")
public class CsgoBox extends CsgoBoxRecord {

    @RelationOneToMany(selfField = "id",targetField = "boxId")
    private List<CsgoBoxGoods> csgoBoxGoods;
}