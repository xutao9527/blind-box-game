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
 * 支付平台管理 实体类。
 *
 * @author bbg
 * @since 2024-07-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "支付平台管理")
@Table("biz_pay_platform")
public class BizPayPlatformRecord extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @Schema(description = "主键")
    private Long id;

    /**
     * 支付名称
     */
    @Schema(description = "支付名称")
    private String payName;

    /**
     * 支付别名
     */
    @Schema(description = "支付别名")
    private String payNameAlias;

    /**
     * 支付图标
     */
    @Schema(description = "支付图标")
    private String payImageUrl;

    /**
     * 支付编码
     */
    @Schema(description = "支付编码")
    private String payCode;

    /**
     * 支付类型
     */
    @Schema(description = "支付类型")
    private String payType;

    /**
     * 支付币种
     */
    @Schema(description = "支付币种")
    private String payCurrency;

    /**
     * 支付限额
     */
    @Schema(description = "支付限额")
    private String payAmountLimit;

    /**
     * 支付描述
     */
    @Schema(description = "支付描述")
    private String payRemark;

    /**
     * 调用引擎
     */
    @Schema(description = "调用引擎")
    private String callEngine;

    /**
     * 调用参数
     */
    @Schema(description = "调用参数")
    private String callArg;

    /**
     * 调用实例[Bean]
     */
    @Schema(description = "调用实例[Bean]")
    private String callBeanName;

    /**
     * 调用引擎脚本
     */
    @Schema(description = "调用引擎脚本")
    private String callContent;

    /**
     * 调用引擎重载
     */
    @Schema(description = "调用引擎重载")
    private Boolean callReload;

    /**
     * 回调引擎
     */
    @Schema(description = "回调引擎")
    private String callbackEngine;

    /**
     * 回调参数
     */
    @Schema(description = "回调参数")
    private String callbackArg;

    /**
     * 回调实例[Bean]
     */
    @Schema(description = "回调实例[Bean]")
    private String callbackBeanName;

    /**
     * 回调引擎内容
     */
    @Schema(description = "回调引擎内容")
    private String callbackContent;

    /**
     * 回调引擎重载
     */
    @Schema(description = "回调引擎重载")
    private Boolean callbackReload;

    /**
     * 回调白名单
     */
    @Schema(description = "回调白名单")
    private String callbackIp;

    /**
     * 查询引擎
     */
    @Schema(description = "查询引擎")
    private String queryEngine;

    /**
     * 查询参数
     */
    @Schema(description = "查询参数")
    private String queryArg;

    /**
     * 查询实例[Bean]
     */
    @Schema(description = "查询实例[Bean]")
    private String queryBeanName;

    /**
     * 查询引擎内容
     */
    @Schema(description = "查询引擎内容")
    private String queryContent;

    /**
     * 查询引擎重载
     */
    @Schema(description = "查询引擎重载")
    private Boolean queryReload;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

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
