package com.bbg.admin.controller.csgo;

import com.bbg.admin.base.controller.csgo.BaseCsgoBoxController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * CSGO箱子 控制层。
 *
 * @author bbg
 * @since 2024-05-02
 */
@RestController
@Tag(name = "CSGO箱子接口")
@RequestMapping("/csgoBox")
public class CsgoBoxController extends BaseCsgoBoxController {

}