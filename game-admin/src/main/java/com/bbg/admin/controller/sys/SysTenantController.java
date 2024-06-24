package com.bbg.admin.controller.sys;

import com.bbg.admin.base.controller.sys.BaseSysTenantController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 系统租户 控制层。
 *
 * @author bbg
 * @since 2024-06-24
 */
@RestController
@Tag(name = "系统租户接口")
@RequestMapping("/sysTenant")
public class SysTenantController extends BaseSysTenantController {

}