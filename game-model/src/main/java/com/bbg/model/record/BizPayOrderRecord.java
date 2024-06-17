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
 * 支付订单 实体类。
 *
 * @author bbg
 * @since 2024-06-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "支付订单")
@Table("biz_pay_order")
public class BizPayOrderRecord extends BaseModel implements Serializable {

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
     * 用户手机
     */
    @Schema(description = "用户手机")
    private String userMobile;

    /**
     * 支付订单号
     */
    @Schema(description = "支付订单号")
    private String payNo;

    /**
     * 支付平台编号
     */
    @Schema(description = "支付平台编号")
    private Long payPlatformId;

    /**
     * 支付币种
     */
    @Schema(description = "支付币种")
    private String payCurrency;

    /**
     * 支付金额
     */
    @Schema(description = "支付金额")
    private BigDecimal payMoney;

    /**
     * 回调时间
     */
    @Schema(description = "回调时间")
    private LocalDateTime callbackTime;

    /**
     * 回调内容
     */
    @Schema(description = "回调内容")
    private String callbackContent;

    /**
     * 支付订单描述
     */
    @Schema(description = "支付订单描述")
    private String payOrderRemark;

    /**
     * 支付状态
     */
    @Schema(description = "支付状态")
    private String payStatus;

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
