package com.bbg.admin.controller.sys;

import com.bbg.admin.base.controller.sys.BaseSysMenuController;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 系统菜单 控制层。
 *
 * @author bbg
 * @since 2024-04-25
 */
@RestController
@Tag(name = "系统菜单接口")
@RequestMapping("/sysMenu")
@Aspect
public class SysMenuController extends BaseSysMenuController {


}