package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.model.csgo.CsgoRollGood;
import com.bbg.core.service.csgo.CsgoRollGoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Roll房间装备 控制层。
 *
 * @author bbg
 * @since 2024-05-16
 */
@RestController
@Tag(name = "Roll房间装备接口")
@RequestMapping("/csgoRollGood")
@RequiredArgsConstructor
public class CsgoRollGoodController extends BaseController {

    public final CsgoRollGoodService csgoRollGoodService;


}