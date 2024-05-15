package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;

import com.bbg.box.service.biz.BizUserService;
import com.bbg.core.box.dto.BattleRoomDto;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoBattleRoom;
import com.bbg.box.service.csgo.CsgoBattleRoomService;
import com.bbg.core.entity.ApiRet;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    CsgoBattleRoomService csgoBattleRoomService;
    @Autowired
    BizUserService bizUserService;

    @PostMapping("create")
    @Operation(description = "创建对战房间")
    public ApiRet<BattleRoomDto.BattleRoomRes> create(@Validated @RequestBody BattleRoomDto.CreateRoomReq model) {
        BizUser bizUser = getCurrentUser();
        return csgoBattleRoomService.createRoom(bizUser, model);
    }

    @GetMapping("join")
    @Operation(description = "加入对战房间")
    public ApiRet<BattleRoomDto.BattleRoomRes> join(@NotNull @RequestParam("roomId") Long roomId) {
        BizUser bizUser = getCurrentUser();
        return csgoBattleRoomService.joinRoom(bizUser, roomId);
    }

    @GetMapping("joinByUserId")
    @Operation(description = "加入对战房间(用户Id-测试)")
    public ApiRet<BattleRoomDto.BattleRoomRes> join(@NotNull @RequestParam("roomId") Long roomId, @NotNull @RequestParam("userId") Long userId) {
        BizUser bizUser = bizUserService.getById(userId);
        if (bizUser != null) {
            return csgoBattleRoomService.joinRoom(bizUser, roomId);
        }
        return ApiRet.buildNo("用户不存在!");
    }

    @PostMapping("getInfo")
    @Operation(description = "获得对战房间")
    public ApiRet<BattleRoomDto.BattleRoomRes> getInfo() {
        BizUser bizUser = getCurrentUser();
        BattleRoomDto.BattleRoomRes createBattleRoomRes = new BattleRoomDto.BattleRoomRes();
        return ApiRet.buildOk(createBattleRoomRes);
    }

    @PostMapping("getRooms")
    @Operation(description = "获取对战房间列表")
    public ApiRet<BattleRoomDto.GetRoomListRes> getRooms(@RequestBody BattleRoomDto.GetRoomListReq model) {
        BizUser bizUser = getCurrentUser();
        BattleRoomDto.GetRoomListRes createBattleRoomRes = new BattleRoomDto.GetRoomListRes();
        return ApiRet.buildOk(createBattleRoomRes);
    }
}