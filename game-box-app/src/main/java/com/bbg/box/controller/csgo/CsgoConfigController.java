package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.model.csgo.CsgoConfig;
import com.bbg.core.service.csgo.CsgoConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 游戏配置 控制层。
 *
 * @author bbg
 * @since 2024-05-03
 */
@RestController
@Tag(name = "游戏配置接口")
@RequestMapping("/csgoConfig")
@RequiredArgsConstructor
public class CsgoConfigController extends BaseController<CsgoConfig> {

    public final CsgoConfigService csgoConfigService;


}