package com.bbg.model.record;

import com.bbg.model.base.BaseModel;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * CSGO用户信息 实体类。
 *
 * @author bbg
 * @since 2024-05-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "CSGO用户信息")
@Table("csgo_user_info")
public class CsgoUserInfoRecord extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @Schema(description = "主键")
    private Long id;

    /**
     * 用户编号
     */
    @Schema(description = "用户编号")
    private Long userId;

    /**
     * 推广码
     */
    @Schema(description = "推广码")
    private String spreadCode;

    /**
     * 邀请码
     */
    @Schema(description = "邀请码")
    private String invitationCode;

    /**
     * 渠道
     */
    @Schema(description = "渠道")
    private String channelCode;

    /**
     * 身份证姓名
     */
    @Schema(description = "身份证姓名")
    private String idCardName;

    /**
     * 身份证号码
     */
    @Schema(description = "身份证号码")
    private String idCardNo;

    /**
     * 配置值
     */
    @Schema(description = "配置值")
    private String steamId;

    /**
     * 配置描述
     */
    @Schema(description = "配置描述")
    private String steamLink;

    /**
     * 最后登录IP
     */
    @Schema(description = "最后登录IP")
    private String lastLoginIp;

    /**
     * 秘密哈希
     */
    @Schema(description = "秘密哈希")
    private String secretHash;

    /**
     * 秘密盐值
     */
    @Schema(description = "秘密盐值")
    private String secretSalt;

    /**
     * 公共哈希
     */
    @Schema(description = "公共哈希")
    private String publicHash;

    /**
     * 客户端种子
     */
    @Schema(description = "客户端种子")
    private String clientSeed;

    /**
     * 轮数
     */
    @Schema(description = "轮数")
    private Integer roundNumber;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

}
