package com.bbg.box.controller.biz;

import com.bbg.box.base.BaseController;
import com.bbg.core.box.dto.LoginDto;
import com.bbg.model.biz.BizUser;
import com.bbg.box.service.biz.BizUserService;
import com.bbg.core.entity.ApiRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

/**
 * 业务用户 控制层。
 *
 * @author bbg
 * @since 2024-05-03
 */
@RestController
@Tag(name = "业务用户接口")
@RequestMapping("/bizUser")
public class BizUserController extends BaseController<BizUser, BizUserService> {
    @Autowired
    protected  BizUserService bizUserService;

    @PostMapping("login")
    @Operation(description = "用户登录")
    public ApiRet<LoginDto.LoginRes> login(@RequestBody LoginDto.LoginReq loginReq) {
        System.out.println(loginReq);
        return ApiRet.buildOk(LoginDto.LoginRes.builder().build());
    }

    @GetMapping("logout")
    @Operation(description = "管理员登出")
    public ApiRet<String> logout() {
        String token = request.getHeader("token");
        if (token != null) {
            redisService.adminLogout(token);
        }
        return ApiRet.buildOk(token);
    }

}