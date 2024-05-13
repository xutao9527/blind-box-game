package com.bbg.admin.controller.csgo;

import com.bbg.admin.base.controller.csgo.BaseCsgoRobotController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 机器人 控制层。
 *
 * @author bbg
 * @since 2024-05-13
 */
@RestController
@Tag(name = "机器人接口")
@RequestMapping("/csgoRobot")
public class CsgoRobotController extends BaseCsgoRobotController {

}