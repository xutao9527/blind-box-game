package com.bbg.admin.controller.csgo;

import com.bbg.admin.base.controller.csgo.BaseCsgoBoxGoodsController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * CSGO箱子商品 控制层。
 *
 * @author bbg
 * @since 2024-05-02
 */
@RestController
@Tag(name = "CSGO箱子商品接口")
@RequestMapping("/csgoBoxGoods")
public class CsgoBoxGoodsController extends BaseCsgoBoxGoodsController {

}