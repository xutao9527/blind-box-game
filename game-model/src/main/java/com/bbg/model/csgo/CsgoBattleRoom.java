package com.bbg.model.csgo;

import com.bbg.model.record.CsgoBattleRoomRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "对战房间")
public class CsgoBattleRoom extends CsgoBattleRoomRecord {

}