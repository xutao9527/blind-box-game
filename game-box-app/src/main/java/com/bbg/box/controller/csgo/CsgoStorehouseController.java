package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.model.csgo.CsgoStorehouse;
import com.bbg.core.service.csgo.CsgoStorehouseService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 个人装备仓库 控制层。
 *
 * @author bbg
 * @since 2024-05-04
 */
@RestController
@Tag(name = "个人装备仓库接口")
@RequestMapping("/csgoStorehouse")
@RequiredArgsConstructor
public class CsgoStorehouseController extends BaseController<CsgoStorehouse> {

    public final CsgoStorehouseService csgoStorehouseService;


}