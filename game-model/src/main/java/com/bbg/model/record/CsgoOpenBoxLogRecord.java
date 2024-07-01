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
 * CSGO开箱日志 实体类。
 *
 * @author bbg
 * @since 2024-07-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "CSGO开箱日志")
@Table("csgo_open_box_log")
public class CsgoOpenBoxLogRecord extends BaseModel implements Serializable {

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
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String userNickName;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String userPhoto;

    /**
     * 开箱时间
     */
    @Schema(description = "开箱时间")
    private LocalDateTime openBoxTime;

    /**
     * 箱子编号
     */
    @Schema(description = "箱子编号")
    private Long boxId;

    /**
     * 箱子名称
     */
    @Schema(description = "箱子名称")
    private String boxName;

    /**
     * 箱子别名
     */
    @Schema(description = "箱子别名")
    private String boxNameAlias;

    /**
     * 箱子图片
     */
    @Schema(description = "箱子图片")
    private String boxImageUrl;

    /**
     * 箱子价格
     */
    @Schema(description = "箱子价格")
    private BigDecimal boxPrice;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private Long goodId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodName;

    /**
     * 商品别名
     */
    @Schema(description = "商品别名")
    private String goodNameAlias;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String goodImageUrl;

    /**
     * 商品价格
     */
    @Schema(description = "商品价格")
    private BigDecimal goodPrice;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}
