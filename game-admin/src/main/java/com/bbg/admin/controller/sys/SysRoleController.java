package com.bbg.admin.controller.sys;

import com.bbg.admin.base.controller.sys.BaseSysRoleController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 系统角色 控制层。
 *
 * @author bbg
 * @since 2024-04-25
 */
@RestController
@Tag(name = "系统角色接口")
@RequestMapping("/sysRole")
public class SysRoleController extends BaseSysRoleController {

}