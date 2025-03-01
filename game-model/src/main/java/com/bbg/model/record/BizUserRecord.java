package com.bbg.model.record;

import com.bbg.model.base.BaseModel;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 业务用户 实体类。
 *
 * @author bbg
 * @since 2024-07-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "业务用户")
@Table("biz_user")
public class BizUserRecord extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @Schema(description = "主键")
    private Long id;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String mobile;

    /**
     * 登录账号
     */
    @Schema(description = "登录账号")
    private String account;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickName;

    /**
     * 金额
     */
    @Schema(description = "金额")
    private BigDecimal money;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String photo;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 生日
     */
    @Schema(description = "生日")
    private LocalDateTime birthday;

    /**
     * 推广码
     */
    @Schema(description = "推广码")
    private String promoCode;

    /**
     * 邀请码
     */
    @Schema(description = "邀请码")
    private String invitationCode;

    /**
     * 渠道码
     */
    @Schema(description = "渠道码")
    private String channelCode;

    /**
     * 类型
     */
    @Schema(description = "类型")
    private String type;

    /**
     * 状态:1=启动,0=禁用
     */
    @Schema(description = "状态:1=启动,0=禁用")
    private Boolean enable;

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

    /**
     * 逻辑删除
     */
    @Schema(description = "逻辑删除")
    private Boolean isDeleted;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}
