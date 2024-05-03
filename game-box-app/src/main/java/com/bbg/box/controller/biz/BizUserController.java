package com.bbg.box.controller.biz;

import com.bbg.box.base.BaseController;
import com.bbg.core.box.dto.LoginDto;
import com.bbg.model.biz.BizUser;
import com.bbg.box.service.biz.BizUserService;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.sys.SysUser;
import com.mybatisflex.core.constant.SqlOperator;
import com.mybatisflex.core.mask.MaskManager;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.UUID;

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
        LoginDto.LoginRes loginRes = new LoginDto.LoginRes();
        QueryWrapper queryWrapper = QueryWrapper.create(new BizUser().setMobile(loginReq.getMobile()));
        BizUser bizUser = MaskManager.execWithoutMask(() -> bizUserService.getOne(queryWrapper));
        if (null != bizUser && bizUser.getEnable() && bizUser.getPassword().equals(loginReq.getPassword())) {
            bizUser.setPassword(null);
            String token = redisService.userLogin(bizUser);
            loginRes.setBizUser(bizUser).setToken(token);
            return ApiRet.buildOk(loginRes);
        }
        return ApiRet.buildNo(loginRes,"用户不存在!");
    }

    @GetMapping("logout")
    @Operation(description = "管理员登出")
    public ApiRet<String> logout() {
        String token = request.getHeader("token");
        if (token != null) {
            redisService.userLogout(token);
        }
        return ApiRet.buildOk(token);
    }

}