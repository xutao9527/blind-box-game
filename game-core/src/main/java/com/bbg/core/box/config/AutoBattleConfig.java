package com.bbg.core.box.config;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;


@Data
public class AutoBattleConfig {
    @JSONField(name = "create_room_interval")
    public int createRoomInterval;
    @JSONField(name = "max_wait_room_size")
    public int maxWaitRoomSize;
    @JSONField(name = "join_room_interval")
    public int joinRoomInterval;
}
