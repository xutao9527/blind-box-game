package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.model.csgo.CsgoBattleRoomGood;
import com.bbg.core.service.csgo.CsgoBattleRoomGoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 对战房间获取装备记录 控制层。
 *
 * @author bbg
 * @since 2024-05-13
 */
@RestController
@Tag(name = "对战房间获取装备记录接口")
@RequestMapping("/csgoBattleRoomGood")
@RequiredArgsConstructor
public class CsgoBattleRoomGoodController extends BaseController {

    public final CsgoBattleRoomGoodService csgoBattleRoomGoodService;


}