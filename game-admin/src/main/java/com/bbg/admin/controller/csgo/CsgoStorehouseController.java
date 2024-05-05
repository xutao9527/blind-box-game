package com.bbg.admin.controller.csgo;

import com.bbg.admin.base.controller.csgo.BaseCsgoStorehouseController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 装备仓库 控制层。
 *
 * @author bbg
 * @since 2024-05-05
 */
@RestController
@Tag(name = "装备仓库接口")
@RequestMapping("/csgoStorehouse")
public class CsgoStorehouseController extends BaseCsgoStorehouseController {

}