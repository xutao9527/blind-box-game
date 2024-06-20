package com.bbg.admin.controller.biz;

import com.bbg.admin.base.controller.biz.BaseBizPayPlatformController;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.biz.BizPayPlatform;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

/**
 * 支付平台管理 控制层。
 *
 * @author bbg
 * @since 2024-06-14
 */
@RestController
@Tag(name = "支付平台管理接口")
@RequestMapping("/bizPayPlatform")
public class BizPayPlatformController extends BaseBizPayPlatformController {

    @PostMapping("debug")
    @Operation(summary = "支付调试接口", description = "支付调试接口")
    public ApiRet<Void> debug(@RequestBody DebugData debugData) {
        System.out.println(debugData);
        return ApiRet.buildOk();
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "调试数据")
    public static class DebugData {
        @Schema(description = "调试对象")
        String debugTarget;
        @Schema(description = "调试请求头")
        Map<String, Object> headers;
        @Schema(description = "调试请求参数")
        Map<String, Object> params;
    }
}