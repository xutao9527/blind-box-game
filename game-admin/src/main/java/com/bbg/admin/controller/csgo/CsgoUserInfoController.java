package com.bbg.admin.controller.csgo;

import com.bbg.admin.base.controller.csgo.BaseCsgoUserInfoController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * CSGO用户信息 控制层。
 *
 * @author bbg
 * @since 2024-05-05
 */
@RestController
@Tag(name = "CSGO用户信息接口")
@RequestMapping("/csgoUserInfo")
public class CsgoUserInfoController extends BaseCsgoUserInfoController {

}