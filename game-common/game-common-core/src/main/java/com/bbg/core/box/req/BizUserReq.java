package com.bbg.core.box.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "业务用户")
public class BizUserReq {

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
