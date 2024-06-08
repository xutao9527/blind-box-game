package com.bbg.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "Oss签名信息")
public class OssSignInfo {
    /**
     * 访问ID
     */
    private String accessId;
    /**
     * 许可
     */
    private String policy;
    /**
     * 签名
     */
    private String signature;
    /**
     * 路径
     */
    private String ossDir;
    /**
     * 请求地址
     */
    private String baseUrlPath;
}
