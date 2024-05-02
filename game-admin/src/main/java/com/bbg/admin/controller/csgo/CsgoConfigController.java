package com.bbg.admin.controller.csgo;

import com.bbg.admin.base.controller.csgo.BaseCsgoConfigController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 游戏配置 控制层。
 *
 * @author bbg
 * @since 2024-04-25
 */
@RestController
@Tag(name = "游戏配置接口")
@RequestMapping("/csgoConfig")
public class CsgoConfigController extends BaseCsgoConfigController {

}