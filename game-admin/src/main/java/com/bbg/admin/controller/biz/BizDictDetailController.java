package com.bbg.admin.controller.biz;

import com.bbg.admin.base.controller.biz.BaseBizDictDetailController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 系统字典详情 控制层。
 *
 * @author bbg
 * @since 2024-04-27
 */
@RestController
@Tag(name = "系统字典详情接口")
@RequestMapping("/bizDictDetail")
public class BizDictDetailController extends BaseBizDictDetailController {

}