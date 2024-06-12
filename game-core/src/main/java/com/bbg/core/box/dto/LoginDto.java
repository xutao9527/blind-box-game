package com.bbg.core.box.dto;

import com.bbg.model.biz.BizUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

public class LoginDto {

    @Data
    @Accessors(chain = true)
    @Schema(description = "登录参数")
    public static class LoginReq implements Serializable {
        /**
         * 手机号
         */
        @Schema(description = "手机号")
        private String mobile;

        /**
         * 密码
         */
        @Schema(description = "密码")
        private String password;
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "登录结果")
    public static class LoginRes implements Serializable{
        /**
         * 用户
         */
        BizUser bizUser;

        /**
         * 用户token
         */
        @Schema(description = "密码")
        private String token;
    }


    @Data
    @Accessors(chain = true)
    @Schema(description = "注册参数")
    public static class RegisterReq implements Serializable {
        /**
         * 手机号
         */
        @Schema(description = "手机号")
        private String mobile;

        /**
         * 密码
         */
        @Schema(description = "密码")
        private String password;

        /**
         * 验证码
         */
        @Schema(description = "验证码")
        private String code;
    }
}
