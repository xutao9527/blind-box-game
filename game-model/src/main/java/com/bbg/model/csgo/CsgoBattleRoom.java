package com.bbg.model.csgo;

import com.bbg.model.record.CsgoBattleRoomRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "对战房间")
public class CsgoBattleRoom extends CsgoBattleRoomRecord {

    @Schema(description = "参数对战的箱子")
    List<CsgoBattleRoomBox> roomBoxes = new ArrayList<>();

    @Schema(description = "参数对战的用户")
    List<CsgoBattleRoomUser> roomUsers = new ArrayList<>();

    @Schema(description = "参数对战的战果")
    List<CsgoBattleRoomGood> roomGoods = new ArrayList<>();
}