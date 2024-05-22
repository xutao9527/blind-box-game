package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.model.csgo.CsgoRollUser;
import com.bbg.box.service.csgo.CsgoRollUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Roll房间用户 控制层。
 *
 * @author bbg
 * @since 2024-05-16
 */
@RestController
@Tag(name = "Roll房间用户接口")
@RequestMapping("/csgoRollUser")
@RequiredArgsConstructor
public class CsgoRollUserController extends BaseController<CsgoRollUser> {

    public final CsgoRollUserService csgoRollUserService;


}