package com.bbg.admin.controller.biz;

import com.bbg.admin.base.controller.biz.BaseBizDictController;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.biz.BizDict;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.Serializable;

/**
 * 系统字典 控制层。
 *
 * @author bbg
 * @since 2024-04-27
 */
@RestController
@Tag(name = "系统字典接口")
@RequestMapping("/bizDict")
public class BizDictController extends BaseBizDictController {

    @GetMapping("getDict/{tag}")
    @Operation(description = "根据字典标识获取")
    public ApiRet<BizDict> getDictByTag(@PathVariable(name = "tag") @Parameter(description = "字典标识") String tag) {
        return ApiRet.buildOk(bizDictService.getDictByTag(tag));
    }
}