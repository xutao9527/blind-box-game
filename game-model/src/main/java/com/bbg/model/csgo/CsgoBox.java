package com.bbg.model.csgo;

import com.bbg.model.record.CsgoBoxRecord;

import com.mybatisflex.annotation.RelationOneToMany;
import com.mybatisflex.annotation.SetListener;
import com.mybatisflex.annotation.Table;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "CSGO箱子")
public class CsgoBox extends CsgoBoxRecord {

    @RelationOneToMany(selfField = "id",targetField = "boxId",orderBy = "sort")
    private List<CsgoBoxGoods> csgoBoxGoods;


}