package com.bbg.admin.third.pay;

import com.bbg.core.entity.ApiRet;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@FeignClient(name = "game-third-server",path = "/pay",configuration = PayService.PayServiceInterceptor.class)
public interface PayService {

    // 定义一个线程安全的map集合对象
    Map<String, String> headers = new ConcurrentHashMap<>();

    @GetMapping("call")
    @Operation(summary = "支付请求", description = "支付请求")
    ApiRet<Object> call(
            @RequestParam("payCode") @Parameter(description = "支付编码") @NotNull String payCode,
            @RequestParam("money") @Parameter(description = "支付金额") @NotNull BigDecimal money
    );

    @GetMapping("callback")
    @Operation(summary = "支付回调", description = "支付回调")
    ApiRet<Object> callback(
            @RequestParam("payCode") @Parameter(description = "支付编码") @NotNull String payCode
    );

    @GetMapping("orderQuery")
    @Operation(summary = "支付订单查询", description = "支付订单查询")
    ApiRet<Object> queryOrder(
            @RequestParam("payNo") @Parameter(description = "支付订单号") @NotNull String payNo
    );

    default void setHeaders(Map<String,String> headers) {
        PayService.headers.putAll(headers);
    }

    class PayServiceInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate requestTemplate) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)  RequestContextHolder.getRequestAttributes();
            for (Map.Entry<String, String> entry : PayService.headers.entrySet()) {
                requestTemplate.header(entry.getKey(), entry.getValue());
            }
            if (servletRequestAttributes != null) {
                HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
                Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String headerName = headerNames.nextElement();
                        String headerValue = httpServletRequest.getHeader(headerName);
                        requestTemplate.header(headerName, headerValue);
                    }
                }
            }
        }
    }
}
