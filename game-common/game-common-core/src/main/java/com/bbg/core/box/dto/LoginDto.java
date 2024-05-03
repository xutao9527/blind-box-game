package com.bbg.core.box.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

public class LoginDto {

    @Data
    @Builder
    @Schema(description = "登录参数")
    public static class LoginReq{
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
    @Builder
    @Schema(description = "登录结果")
    public static class LoginRes{
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
}
