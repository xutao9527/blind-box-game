package com.bbg.admin.controller.biz;

import com.bbg.admin.base.controller.biz.BaseBizUserController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 业务用户 控制层。
 *
 * @author bbg
 * @since 2024-04-25
 */
@RestController
@Tag(name = "业务用户接口")
@RequestMapping("/bizUser")
public class BizUserController extends BaseBizUserController {

}