package com.bbg.box.third.pay;

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

@FeignClient(name = "game-third-server",path = "/pay",configuration = PayService.PayServiceInterceptor.class)
public interface PayService {

    @GetMapping("call")
    @Operation(summary = "支付请求", description = "支付请求")
    ApiRet<Object> call(
            @RequestParam("payCode") @Parameter(description = "支付编码") @NotNull String payCode,
            @RequestParam("money") @Parameter(description = "支付金额") @NotNull BigDecimal money
    );

    class  PayServiceInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate requestTemplate) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)  RequestContextHolder.getRequestAttributes();;
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
