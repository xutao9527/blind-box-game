package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;

import com.bbg.core.box.dto.BattleRoomDto;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoBattleRoom;
import com.bbg.box.service.csgo.CsgoBattleRoomService;
import com.bbg.core.entity.ApiRet;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 对战房间 控制层。
 *
 * @author bbg
 * @since 2024-05-13
 */
@RestController
@Tag(name = "对战房间接口")
@RequestMapping("/csgoBattleRoom")
public class CsgoBattleRoomController extends BaseController<CsgoBattleRoom, CsgoBattleRoomService> {
    @Autowired
    protected  CsgoBattleRoomService csgoBattleRoomService;

    @PostMapping("create")
    @Operation(description = "创建对战房间")
    public ApiRet<BattleRoomDto.CreateRoomRes> create(@Validated @RequestBody BattleRoomDto.CreateRoomReq model){
        BizUser bizUser = getCurrentUser();
        return csgoBattleRoomService.createRoom(bizUser,model);
    }

    @PostMapping("getInfo")
    @Operation(description = "获得对战房间")
    public ApiRet<BattleRoomDto.GetRoomRes> getInfo(@RequestBody BattleRoomDto.GetRoomReq model){
        BizUser bizUser = getCurrentUser();
        BattleRoomDto.GetRoomRes  createBattleRoomRes = new BattleRoomDto.GetRoomRes();
        return ApiRet.buildOk(createBattleRoomRes);
    }

    @PostMapping("getRooms")
    @Operation(description = "获取对战房间列表")
    public ApiRet<BattleRoomDto.GetRoomRes> getRooms(@RequestBody BattleRoomDto.GetRoomRes model){
        BizUser bizUser = getCurrentUser();
        BattleRoomDto.GetRoomRes  createBattleRoomRes = new BattleRoomDto.GetRoomRes();
        return ApiRet.buildOk(createBattleRoomRes);
    }
}