package com.bbg.admin.controller.biz;

import com.bbg.admin.base.controller.biz.BaseBizConfigController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 平台配置 控制层。
 *
 * @author bbg
 * @since 2024-06-08
 */
@RestController
@Tag(name = "平台配置接口")
@RequestMapping("/bizConfig")
public class BizConfigController extends BaseBizConfigController {

}