package com.bbg.third.controller;

import com.bbg.core.entity.ApiRet;
import com.bbg.third.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "在线支付接口")
@RequestMapping("/pay")
@RequiredArgsConstructor
public class OnlinePayController extends BaseController {

    @GetMapping("call")
    @Operation(description = "支付请求")
    public ApiRet<String> call() {
        return ApiRet.buildOk("call");
    }

    @GetMapping("callback")
    @Operation(description = "支付回调")
    public ApiRet<String> callback() {
        return ApiRet.buildOk("callback");
    }
}
