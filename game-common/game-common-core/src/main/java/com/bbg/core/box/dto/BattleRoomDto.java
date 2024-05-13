package com.bbg.core.box.dto;

import com.bbg.model.csgo.CsgoBattleRoom;
import com.bbg.model.csgo.CsgoStorehouse;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

public class BattleRoomDto {
    @Data
    @Accessors(chain = true)
    @Schema(description = "获得对战房间列表")
    public static class BattleRoomReq implements Serializable {
        @Schema(description = "页数")
        private Number pageNumber;
        @Schema(description = "页大小")
        private Number pageSize;
        @Schema(description = "对战模式")
        private String battleModel;
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "获得对战房间结果")
    public static class BattleRoomRes implements Serializable {
        @Schema(description = "房间列表")
        private Page<CsgoBattleRoom> csgoBattleRoomPage;
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "创建对战房间")
    public static class createBattleRoomReq implements Serializable {
        @Schema(description = "对战模式")
        private String battleModel;
        @Schema(description = "房间人数")
        private int peopleNumber;
        @Schema(description = "房间箱子编号集合")
        private long[] boxesId;
        @Schema(description = "机器人编号集合")
        private long[] robotsId;
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "创建对战房间结果")
    public static class createBattleRoomRes implements Serializable {
        @Schema(description = "对战房间信息")
        private CsgoBattleRoom csgoBattleRoom;
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "获得对战房间")
    public static class GetBattleRoomReq implements Serializable {
        @Schema(description = "对战房间编号")
        private long roomId;
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "获得对战房间结果")
    public static class GetBattleRoomRes implements Serializable {
        @Schema(description = "对战房间信息")
        private CsgoBattleRoom csgoBattleRoom;
    }

}
