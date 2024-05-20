package com.bbg.box.service.csgo;

import com.bbg.core.annotation.RedisCache;
import com.bbg.core.box.dto.BattleRoomDto;
import com.bbg.core.constants.KeyConst;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.biz.BizUser;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.bbg.model.csgo.CsgoBattleRoom;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.bbg.box.service.impl.csgo.CsgoBattleRoomServiceImpl.ROOM_INFO_LIVE_TIME;

/**
 * 对战房间 服务层。
 *
 * @author bbg
 * @since 2024-05-13
 */
public interface CsgoBattleRoomService extends IService<CsgoBattleRoom> {

    ApiRet<BattleRoomDto.BattleRoomRes> createRoom(BizUser bizuser, BattleRoomDto.CreateRoomReq createRoomReq, long roomId);

    ApiRet<BattleRoomDto.BattleRoomRes> joinRoom(BizUser bizuser, Long roomId);

    CsgoBattleRoom getInfo(Long roomId);

    Page<CsgoBattleRoom> getRoomList(BattleRoomDto.GetRoomListReq getRoomListReq);
}
