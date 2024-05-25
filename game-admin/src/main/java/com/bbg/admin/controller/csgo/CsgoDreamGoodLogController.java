package com.bbg.admin.controller.csgo;

import com.bbg.admin.base.controller.csgo.BaseCsgoDreamGoodLogController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * CSGO追梦日志 控制层。
 *
 * @author bbg
 * @since 2024-05-25
 */
@RestController
@Tag(name = "CSGO追梦日志接口")
@RequestMapping("/csgoDreamGoodLog")
public class CsgoDreamGoodLogController extends BaseCsgoDreamGoodLogController {

}