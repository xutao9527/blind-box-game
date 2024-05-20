package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.model.csgo.CsgoRobot;
import com.bbg.box.service.csgo.CsgoRobotService;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.ReqParams;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.constant.SqlOperator;
import com.mybatisflex.core.query.SqlOperators;
import lombok.RequiredArgsConstructor;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

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
public class CsgoRobotController extends BaseController<CsgoRobot, CsgoRobotService> {

    public final CsgoRobotService csgoRobotService;

    @GetMapping("list")
    @Operation(description = "获得所有机器人")
    public ApiRet<List<CsgoRobot>> list() {
        return ApiRet.buildOk(csgoRobotService.list(QueryWrapper.create().eq(CsgoRobot::getEnable,true)));
    }
}