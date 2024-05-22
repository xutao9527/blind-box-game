package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.model.csgo.CsgoBattleRoomUser;
import com.bbg.box.service.csgo.CsgoBattleRoomUserService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/csgoBattleRoomUser")
@RequiredArgsConstructor
public class CsgoBattleRoomUserController extends BaseController<CsgoBattleRoomUser> {

    public final CsgoBattleRoomUserService csgoBattleRoomUserService;


}