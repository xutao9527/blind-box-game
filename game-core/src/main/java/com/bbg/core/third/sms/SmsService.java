package com.bbg.core.third.sms;

import com.bbg.core.constrans.KeyConst;
import com.bbg.core.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsService {

    public final RedisService redisService;

    /**
     * 发送短信验证码
     */
    public boolean sendSmsCode(String phone, String code) {
        log.info("send sms code to {} with code: {}", phone, code);
        redisService.set(KeyConst.build(KeyConst.USER_SMS_CODE, phone,true), code, 10L, TimeUnit.MINUTES);
        return true;
    }

    /**
     * 验证短信验证码
     */
    public boolean verifySmsCode(String phone, String code) {
        log.info("verify sms code {} for phone: {}", code, phone);
        String smsCode = (String) redisService.get(KeyConst.build(KeyConst.USER_SMS_CODE, phone,true));
        return smsCode != null && smsCode.equals(code);
    }
}
