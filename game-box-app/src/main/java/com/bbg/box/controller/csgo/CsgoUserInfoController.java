package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.model.csgo.CsgoUserInfo;
import com.bbg.core.service.csgo.CsgoUserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * CSGO用户信息 控制层。
 *
 * @author bbg
 * @since 2024-05-04
 */
@RestController
@Tag(name = "CSGO用户信息接口")
@RequestMapping("/csgoUserInfo")
@RequiredArgsConstructor
public class CsgoUserInfoController extends BaseController {

    public final CsgoUserInfoService csgoUserInfoService;


}