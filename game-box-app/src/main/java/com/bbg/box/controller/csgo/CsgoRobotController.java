package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.core.annotation.RedisCache;
import com.bbg.core.constrans.KeyConst;
import com.bbg.model.csgo.CsgoRobot;
import com.bbg.core.service.csgo.CsgoRobotService;
import com.bbg.core.entity.ApiRet;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

/**
 * 机器人 控制层。
 *
 * @author bbg
 * @since 2024-05-13
 */
@RestController
@Tag(name = "机器人接口")
@RequestMapping("/csgoRobot")
@RequiredArgsConstructor
@Slf4j
public class CsgoRobotController extends BaseController<CsgoRobot> {

    public final CsgoRobotService csgoRobotService;

    @GetMapping("list")
    @Operation(summary = "获得所有机器人", description = "获得所有机器人")
    @RedisCache(key = KeyConst.ROBOT_LIST)
    public ApiRet<List<CsgoRobot>> list() {
        log.info("获得所有机器人");
        return ApiRet.buildOk(csgoRobotService.list(QueryWrapper.create().eq(CsgoRobot::getEnable,true)));
    }
}