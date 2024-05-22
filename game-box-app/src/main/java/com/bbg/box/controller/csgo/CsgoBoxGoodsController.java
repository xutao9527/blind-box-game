package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.model.csgo.CsgoBoxGoods;
import com.bbg.box.service.csgo.CsgoBoxGoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * CSGO箱子商品 控制层。
 *
 * @author bbg
 * @since 2024-05-03
 */
@RestController
@Tag(name = "CSGO箱子商品接口")
@RequestMapping("/csgoBoxGoods")
@RequiredArgsConstructor
public class CsgoBoxGoodsController extends BaseController<CsgoBoxGoods> {

    public final CsgoBoxGoodsService csgoBoxGoodsService;


}