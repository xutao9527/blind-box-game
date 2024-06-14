package com.bbg.admin.controller.biz;

import com.bbg.admin.base.controller.biz.BaseBizPayOrderController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 支付订单 控制层。
 *
 * @author bbg
 * @since 2024-06-14
 */
@RestController
@Tag(name = "支付订单接口")
@RequestMapping("/bizPayOrder")
public class BizPayOrderController extends BaseBizPayOrderController {

}