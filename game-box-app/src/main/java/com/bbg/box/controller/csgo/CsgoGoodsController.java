package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.model.csgo.CsgoGoods;
import com.bbg.core.service.csgo.CsgoGoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * CSGO商品表 控制层。
 *
 * @author bbg
 * @since 2024-05-03
 */
@RestController
@Tag(name = "CSGO商品表接口")
@RequestMapping("/csgoGoods")
@RequiredArgsConstructor
public class CsgoGoodsController extends BaseController {

    public final CsgoGoodsService csgoGoodsService;


}