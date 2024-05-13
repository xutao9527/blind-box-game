package com.bbg.model.csgo;

import com.bbg.model.record.CsgoBattleRoomGoodRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "对战房间获取装备记录")
public class CsgoBattleRoomGood extends CsgoBattleRoomGoodRecord {

}