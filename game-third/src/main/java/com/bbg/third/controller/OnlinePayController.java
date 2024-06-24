package com.bbg.third.controller;

import com.alibaba.fastjson2.JSONArray;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.WebSocketMsg;
import com.bbg.core.feign.socket.WebSocketService;
import com.bbg.core.service.biz.BizDictService;
import com.bbg.core.service.biz.BizPayPlatformService;
import com.bbg.model.biz.BizDict;
import com.bbg.model.biz.BizPayPlatform;
import com.bbg.model.biz.BizUser;
import com.bbg.third.base.BaseController;
import com.bbg.third.engine.PayEngine;
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
import java.util.List;

@RestController
@Tag(name = "在线支付接口")
@RequestMapping("/pay")
@RequiredArgsConstructor
@Validated
public class OnlinePayController extends BaseController {
    public final PayService payService;
    public final BizPayPlatformService bizPayPlatformService;
    public final BizDictService bizDictService;

    public final PayEngine scriptPayEngine;
    public final PayEngine beanPayEngine;



    @GetMapping("call")
    @Operation(summary = "支付请求", description = "支付请求")
    public ApiRet<Object> call(
            @RequestParam("payCode") @Parameter(description = "支付编码") @NotNull String payCode,
            @RequestParam("money") @Parameter(description = "支付金额") @NotNull BigDecimal money
    ) {
        // ----------------------------------------- 前置检查 -----------------------------------------
        BizUser bizUser = getCurrentUser();
        if (bizUser == null) {
            return ApiRet.buildNo("用户未登录");
        }
        BizPayPlatform bizPayPlatform = bizPayPlatformService.getOneByCode(payCode);
        if (bizPayPlatform == null) {
            return ApiRet.buildNo("支付平台不存在");
        }
        try {
            List<BigDecimal> amountLimit = JSONArray.parseArray(bizPayPlatform.getPayAmountLimit(), BigDecimal.class);
            if (amountLimit.stream().noneMatch(amount -> amount.compareTo(money) == 0)) {
                return ApiRet.buildNo("支付金额不在范围内");
            }
        } catch (Exception ignored) {
            return ApiRet.buildNo("支付限额配置错误");
        }
        // ----------------------------------------- 前置检查 -----------------------------------------
        Object result = null;
        BizDict thirdPayEngine = bizDictService.getDictByTag("third_pay_engine");
        if (bizPayPlatform.getCallEngine().equals(thirdPayEngine.getValueByAlias("javascript_engine"))) {
            result = scriptPayEngine.execCall(bizUser, bizPayPlatform, money);
        } else if (bizPayPlatform.getCallEngine().equals(thirdPayEngine.getValueByAlias("java_engine"))) {
            result = beanPayEngine.execCall(bizUser, bizPayPlatform, money);
        }
        return ApiRet.buildOk(result);
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
