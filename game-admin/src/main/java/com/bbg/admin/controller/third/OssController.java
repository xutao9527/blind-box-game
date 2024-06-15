package com.bbg.admin.controller.third;

import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.OssSignInfo;
import com.bbg.core.third.oss.OssService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 对象存储 控制层。
 *
 * @author bbg
 * @since 2024-04-25
 */
@Validated
@RestController
@Tag(name = "第三方对象存储")
@RequestMapping("/oss")
@RequiredArgsConstructor
public class OssController {
    public final OssService ossService;

    @GetMapping("sign")
    @Operation(summary = "获取OSS签名", description = "获取OSS签名")
    public ApiRet<OssSignInfo> sign(@RequestParam("dir") @NotNull(message = "上传目录不能为空") String dir) {
        OssSignInfo ossSignInfo = ossService.doSign(dir);
        return ApiRet.buildOk(ossSignInfo);
    }


}
