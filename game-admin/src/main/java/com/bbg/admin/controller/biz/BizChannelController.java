package com.bbg.admin.controller.biz;

import com.bbg.admin.base.controller.biz.BaseBizChannelController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 渠道管理 控制层。
 *
 * @author bbg
 * @since 2024-06-13
 */
@RestController
@Tag(name = "渠道管理接口")
@RequestMapping("/bizChannel")
public class BizChannelController extends BaseBizChannelController {

}