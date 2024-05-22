package com.bbg.box.controller.biz;

import com.bbg.box.base.BaseController;
import com.bbg.model.biz.BizDict;
import com.bbg.box.service.biz.BizDictService;
import com.bbg.core.entity.ApiRet;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 系统字典 控制层。
 *
 * @author bbg
 * @since 2024-05-03
 */
@RestController
@Tag(name = "系统字典接口")
@RequestMapping("/bizDict")
@RequiredArgsConstructor
public class BizDictController extends BaseController<BizDict> {
    public final BizDictService bizDictService;

    @GetMapping("getDict/{tag}")
    @Operation(description = "根据字典标识获取")
    public ApiRet<BizDict> getDictByTag(@PathVariable(name = "tag") @Parameter(description = "字典标识") String tag) {
        return ApiRet.buildOk(bizDictService.getDictByTag(tag));
    }
}