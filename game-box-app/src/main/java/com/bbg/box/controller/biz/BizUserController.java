package com.bbg.box.controller.biz;

import com.bbg.box.base.BaseController;
import com.bbg.core.box.dto.LoginDto;
import com.bbg.model.biz.BizUser;
import com.bbg.core.service.biz.BizUserService;
import com.bbg.core.entity.ApiRet;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BizUserController extends BaseController<BizUser> {
    @Autowired
    protected BizUserService bizUserService;

    @GetMapping ("getInfo")
    @Operation(description = "获得用户信息")
    public ApiRet<LoginDto.LoginRes> getInfo() {
        LoginDto.LoginRes LoginRes = new LoginDto.LoginRes();
        String token =  request.getHeader("token");
        BizUser bizUser = getCurrentUser();
        LoginRes.setToken(token).setBizUser(bizUser);
        return ApiRet.buildOk(LoginRes);
    }

    @PostMapping("login")
    @Operation(description = "用户登录")
    public ApiRet<LoginDto.LoginRes> login(@RequestBody LoginDto.LoginReq loginReq) {
        LoginDto.LoginRes loginRes = new LoginDto.LoginRes();
        BizUser bizUser = bizUserService.getOneByMobile(loginReq.getMobile());
        if (null != bizUser && bizUser.getEnable() && bizUser.getPassword().equals(loginReq.getPassword())) {
            bizUser = bizUserService.getById(bizUser.getId());
            String token = redisService.userLogin(bizUser);
            loginRes.setBizUser(bizUser).setToken(token);
            return ApiRet.buildOk(loginRes);
        }
        return ApiRet.buildNo(loginRes, "用户不存在!");
    }

    @GetMapping("logout")
    @Operation(description = "用户登出")
    public ApiRet<String> logout() {
        String token = request.getHeader("token");
        if (token != null) {
            redisService.userLogout(token);
        }
        return ApiRet.buildOk(token);
    }

}