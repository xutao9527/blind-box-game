package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.model.csgo.CsgoBattleRoomBox;
import com.bbg.core.service.csgo.CsgoBattleRoomBoxService;
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
@RequestMapping("/csgoBattleRoomBox")
@RequiredArgsConstructor
public class CsgoBattleRoomBoxController extends BaseController<CsgoBattleRoomBox> {

    public final CsgoBattleRoomBoxService csgoBattleRoomBoxService;


}