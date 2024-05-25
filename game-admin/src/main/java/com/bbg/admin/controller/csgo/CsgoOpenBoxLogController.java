package com.bbg.admin.controller.csgo;

import com.bbg.admin.base.controller.csgo.BaseCsgoOpenBoxLogController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * CSGO开箱日志 控制层。
 *
 * @author bbg
 * @since 2024-05-25
 */
@RestController
@Tag(name = "CSGO开箱日志接口")
@RequestMapping("/csgoOpenBoxLog")
public class CsgoOpenBoxLogController extends BaseCsgoOpenBoxLogController {

}