package com.bbg.admin.controller.csgo;

import com.bbg.admin.base.controller.csgo.BaseCsgoBattleRoomBoxController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 对战房间用户 控制层。
 *
 * @author bbg
 * @since 2024-05-13
 */
@RestController
@Tag(name = "对战房间用户接口")
@RequestMapping("/csgoBattleRoomBox")
public class CsgoBattleRoomBoxController extends BaseCsgoBattleRoomBoxController {

}