package com.bbg.core.box.dto;

import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoBattleRoom;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

public class BattleRoomDto {
    @Data
    @Accessors(chain = true)
    @Schema(description = "获得对战房间列表")
    public static class GetRoomListReq implements Serializable {
        @Schema(description = "页数")
        private int pageNumber = 1;
        @Schema(description = "页大小")
        private int pageSize = 10;
        @NotNull(message = "对战模式不能为空")
        @Min(value = 0, message = "对战模式1~2之间")
        @Max(value = 2, message = "对战模式1~2之间")
        @Schema(description = "对战模式:0=所有,1=欧皇,2=非酋")
        private String battleModel;
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "创建对战房间")
    public static class CreateRoomReq implements Serializable {
        @NotNull(message = "对战模式不能为空")
        @Min(value = 1 , message = "对战模式1~2之间")
        @Max(value = 2  ,message = "对战模式1~2之间")
        @Schema(description = "对战模式:1=欧皇,2=非酋")
        private String battleModel;

        @NotNull(message = "对战模式不能为空")
        @Min(value = 2,message = "房间人数2~4之间")
        @Max(value = 4,message = "房间人数2~4之间")
        @Schema(description = "房间人数")
        private int peopleNumber;

        @NotEmpty(message = "箱子编号集合不能为空")
        @Size(min = 1,max = 6,message = "箱子编号集合数量在1~6之间")
        @Schema(description = "箱子编号集合")
        private long[] boxesId;

        @Size(max = 3,message = "机器人数量为0~3之间")
        @Schema(description = "机器人编号集合")
        private long[] robotsId;
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "对战房间结果")
    public static class BattleRoomRes implements Serializable {
        @Schema(description = "对战房间信息")
        private CsgoBattleRoom csgoBattleRoom;
        @Schema(description = "新用户信息")
        private BizUser bizUser;
    }

}
