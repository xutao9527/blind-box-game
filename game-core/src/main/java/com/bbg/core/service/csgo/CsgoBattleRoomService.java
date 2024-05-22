package com.bbg.core.service.csgo;

import com.bbg.core.box.dto.BattleRoomDto;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.biz.BizUser;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.bbg.model.csgo.CsgoBattleRoom;

/**
 * 对战房间 服务层。
 *
 * @author bbg
 * @since 2024-05-22
 */
public interface CsgoBattleRoomService extends IService<CsgoBattleRoom> {

    ApiRet<BattleRoomDto.BattleRoomRes> createRoom(BizUser bizuser, BattleRoomDto.CreateRoomReq createRoomReq, long roomId);

    ApiRet<BattleRoomDto.BattleRoomRes> joinRoom(BizUser bizuser, Long roomId);

    CsgoBattleRoom getInfo(Long roomId);

    Page<CsgoBattleRoom> getRoomList(BattleRoomDto.GetRoomListReq getRoomListReq);
}
