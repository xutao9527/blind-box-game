package com.bbg.box.controller.biz;

import cn.hutool.core.util.RandomUtil;
import com.bbg.box.base.BaseController;
import com.bbg.core.box.dto.LoginDto;
import com.bbg.core.service.biz.BizDataService;
import com.bbg.core.service.biz.BizDictService;
import com.bbg.core.service.csgo.CsgoUserInfoService;
import com.bbg.core.third.sms.SmsService;
import com.bbg.core.utils.FairFactory;
import com.bbg.core.utils.IdTool;
import com.bbg.model.biz.BizData;
import com.bbg.model.biz.BizUser;
import com.bbg.core.service.biz.BizUserService;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.csgo.CsgoUserInfo;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;

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
@Validated
public class BizUserController extends BaseController {
    public final BizUserService bizUserService;
    public final SmsService smsService;
    public final BizDictService bizDictService;
    public final BizDataService dataService;
    public final CsgoUserInfoService csgoUserInfoService;

    @GetMapping("getInfo")
    @Operation(summary = "获得用户信息", description = "获得用户信息")
    public ApiRet<LoginDto.LoginRes> getInfo() {
        LoginDto.LoginRes LoginRes = new LoginDto.LoginRes();
        String token = request.getHeader("token");
        BizUser bizUser = getCurrentUser();
        LoginRes.setToken(token).setBizUser(bizUser);
        return ApiRet.buildOk(LoginRes);
    }

    @SecurityRequirements()
    @PostMapping("loginByPwd")
    @Operation(summary = "用户密码登录", description = "用户密码登录")
    public ApiRet<LoginDto.LoginRes> login(@RequestBody LoginDto.LoginPwdReq loginReq) {
        LoginDto.LoginRes loginRes = new LoginDto.LoginRes();
        BizUser bizUser = bizUserService.getOneByMobile(loginReq.getMobile());
        if(bizUser == null){
            return ApiRet.buildNo("用户不存在!");
        }
        if (bizUser.getEnable() && bizUser.getPassword().equals(loginReq.getPassword())) {
            bizUser = bizUserService.getById(bizUser.getId());
            String token = redisService.userLogin(bizUser);
            loginRes.setBizUser(bizUser).setToken(token);
            return ApiRet.buildOk(loginRes);
        }
        return ApiRet.buildNo(loginRes, "密码错误!");
    }


    @PostMapping("loginByCode")
    @Operation(summary = "用户验证码登录", description = "用户验证码登录")
    public ApiRet<LoginDto.LoginRes> login(@RequestBody LoginDto.LoginCodeReq loginReq) {
        LoginDto.LoginRes loginRes = new LoginDto.LoginRes();
        BizUser bizUser = bizUserService.getOneByMobile(loginReq.getMobile());
        if (null != bizUser && bizUser.getEnable()) {
            if(smsService.verifySmsCode(loginReq.getMobile(), loginReq.getCode())){
                bizUser = bizUserService.getById(bizUser.getId());
                String token = redisService.userLogin(bizUser);
                loginRes.setBizUser(bizUser).setToken(token);
                return ApiRet.buildOk(loginRes);
            }else{
                return ApiRet.buildNo("验证码错误!");
            }
        }
        return ApiRet.buildNo(loginRes, "用户不存在!");
    }

    @GetMapping("logout")
    @Operation(summary = "用户登出", description = "用户登出")
    public ApiRet<String> logout() {
        String token = request.getHeader("token");
        if (token != null) {
            redisService.userLogout(token);
        }
        return ApiRet.buildOk(token);
    }

    @GetMapping ("sendCode")
    @Operation(summary = "发送短信验证码", description = "发送短信验证码")
    public ApiRet<String> sendSms(@RequestParam @NotNull String mobile) {
        // 生成4位数验证码
        String code = RandomUtil.randomNumbers(4);
        boolean isOk = smsService.sendSmsCode(mobile, code);
        if (!isOk) {
            return ApiRet.buildNo("发送失败");
        }
        return ApiRet.buildOk("发送成功");
    }

    @PostMapping("register")
    @Operation(summary = "用户注册", description = "用户注册")
    public ApiRet<String> register(@RequestBody LoginDto.RegisterReq registerReq) {
        return bizUserService.register(registerReq);
    }
}