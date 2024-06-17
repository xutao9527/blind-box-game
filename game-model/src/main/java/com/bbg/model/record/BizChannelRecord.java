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
 * 渠道管理 实体类。
 *
 * @author bbg
 * @since 2024-06-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "渠道管理")
@Table("biz_channel")
public class BizChannelRecord extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @Schema(description = "主键")
    private Long id;

    /**
     * 所属管理
     */
    @Schema(description = "所属管理")
    private Long adminId;

    /**
     * 渠道名称
     */
    @Schema(description = "渠道名称")
    private String channelName;

    /**
     * 渠道域名
     */
    @Schema(description = "渠道域名")
    private String channelDomain;

    /**
     * 渠道码
     */
    @Schema(description = "渠道码")
    private String channelCode;

    /**
     * 渠道描述
     */
    @Schema(description = "渠道描述")
    private String channelRemark;

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

}
