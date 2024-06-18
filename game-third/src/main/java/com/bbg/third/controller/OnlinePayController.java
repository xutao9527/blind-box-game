package com.bbg.third.controller;

import com.bbg.core.entity.ApiRet;
import com.bbg.core.service.biz.BizPayPlatformService;
import com.bbg.model.biz.BizUser;
import com.bbg.third.base.BaseController;
import com.bbg.third.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Tag(name = "在线支付接口")
@RequestMapping("/pay")
@RequiredArgsConstructor
@Validated
public class OnlinePayController extends BaseController {
    public final PayService payService;
    public final BizPayPlatformService bizPayPlatformService;

    @GetMapping("call")
    @Operation(summary = "支付请求", description = "支付请求")
    public ApiRet<Object> call(
            @RequestParam("payCode") @Parameter(description = "支付编码") @NotNull String payCode,
            @RequestParam("money") @Parameter(description = "支付金额") @NotNull BigDecimal money
    ) {
        BizUser bizUser = getCurrentUser();
        return payService.payCall();
    }

    @GetMapping("callback")
    @Operation(summary = "支付回调", description = "支付回调")
    public ApiRet<Object> callback(
            @RequestParam("payCode") @Parameter(description = "支付编码") @NotNull String payCode
    ) {
        return payService.payCallback();
    }

    @GetMapping("orderQuery")
    @Operation(summary = "支付订单查询", description = "支付订单查询")
    public ApiRet<Object> queryOrder(
            @RequestParam("payNo") @Parameter(description = "支付订单号") @NotNull String payNo
    ) {
        return payService.payOrderQuery();
    }
}
