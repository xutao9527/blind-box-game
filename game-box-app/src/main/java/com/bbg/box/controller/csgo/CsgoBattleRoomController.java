package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;

import com.bbg.box.service.biz.BizUserService;
import com.bbg.box.utils.IdTool;
import com.bbg.core.box.dto.BattleRoomDto;
import com.bbg.core.constants.KeyConst;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoBattleRoom;
import com.bbg.box.service.csgo.CsgoBattleRoomService;
import com.bbg.core.entity.ApiRet;
import com.mybatisflex.core.paginate.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 对战房间 控制层。
 *
 * @author bbg
 * @since 2024-05-13
 */
@RestController
@Tag(name = "CS:GO对战接口")
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
        long roomId = IdTool.nextId();
        ApiRet<BattleRoomDto.BattleRoomRes> apiRet;
        try {
            apiRet = csgoBattleRoomService.createRoom(bizUser, model, roomId);
        } catch (Exception e) {
            redisService.delete(KeyConst.build(KeyConst.ROOM_INFO_ID, String.valueOf(roomId)));
            apiRet = ApiRet.buildNo("创建房间异常");
        }
        return apiRet;
    }

    @GetMapping("join")
    @Operation(description = "加入对战房间")
    public ApiRet<BattleRoomDto.BattleRoomRes> join(@NotNull @RequestParam("roomId") Long roomId) {
        ApiRet<BattleRoomDto.BattleRoomRes> apiRet;
        BizUser bizUser = getCurrentUser();
        if(bizUser==null){
            return ApiRet.buildNo("用户不存在");
        }
        try {
            apiRet = csgoBattleRoomService.joinRoom(bizUser, roomId);
        } catch (Exception e) {
            redisService.delete(KeyConst.build(KeyConst.ROOM_INFO_ID, String.valueOf(roomId)));
            apiRet = ApiRet.buildNo("加入房间异常");
        }
        return apiRet;
    }

    @GetMapping("joinByUserId")
    @Operation(description = "加入对战房间(用户Id-测试)")
    public ApiRet<BattleRoomDto.BattleRoomRes> join(@NotNull @RequestParam("roomId") Long roomId, @NotNull @RequestParam("userId") Long userId) {
        BizUser bizUser = bizUserService.getById(userId);
        ApiRet<BattleRoomDto.BattleRoomRes> apiRet;
        if (bizUser != null) {
            try {
                apiRet = csgoBattleRoomService.joinRoom(bizUser, roomId);
            } catch (Exception e) {
                redisService.delete(KeyConst.build(KeyConst.ROOM_INFO_ID, String.valueOf(roomId)));
                apiRet = ApiRet.buildNo("加入房间异常");
            }
            return apiRet;
        }
        return ApiRet.buildNo("用户不存在!");
    }

    @PostMapping("getRoom")
    @Operation(description = "获得对战房间信息")
    public ApiRet<BattleRoomDto.BattleRoomRes> getInfo(@NotNull @RequestParam("roomId") Long roomId) {
        BattleRoomDto.BattleRoomRes createBattleRoomRes = new BattleRoomDto.BattleRoomRes();
        CsgoBattleRoom battleRoom = csgoBattleRoomService.getInfo(roomId);
        createBattleRoomRes.setCsgoBattleRoom(battleRoom);
        return ApiRet.buildOk(createBattleRoomRes);
    }

    @PostMapping("getRoomList")
    @Operation(description = "获取对战房间列表")
    public ApiRet<Page<CsgoBattleRoom>> getRoomList(@RequestBody BattleRoomDto.GetRoomListReq model) {
        return ApiRet.buildOk(csgoBattleRoomService.getRoomList(model));
    }
}