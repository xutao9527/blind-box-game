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
 * CSGO商品表 实体类。
 *
 * @author bbg
 * @since 2024-06-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "CSGO商品表")
@Table("csgo_goods")
public class CsgoGoodsRecord extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    @Schema(description = "主键id")
    private Long id;

    /**
     * 皮肤名称
     */
    @Schema(description = "皮肤名称")
    private String itemName;

    /**
     * 皮肤短名
     */
    @Schema(description = "皮肤短名")
    private String shortName;

    /**
     * 皮肤市场名称
     */
    @Schema(description = "皮肤市场名称")
    private String marketHashName;

    /**
     * 类型
     */
    @Schema(description = "类型")
    private String type;

    /**
     * 类型名称
     */
    @Schema(description = "类型名称")
    private String typeName;

    /**
     * 外观
     */
    @Schema(description = "外观")
    private String exterior;

    /**
     * 外观名称
     */
    @Schema(description = "外观名称")
    private String exteriorName;

    /**
     * 外观颜色
     */
    @Schema(description = "外观颜色")
    private String exteriorColor;

    /**
     * 类别
     */
    @Schema(description = "类别")
    private String quality;

    /**
     * 类别名称
     */
    @Schema(description = "类别名称")
    private String qualityName;

    /**
     * 类别颜色
     */
    @Schema(description = "类别颜色")
    private String qualityColor;

    /**
     * 品质
     */
    @Schema(description = "品质")
    private String rarity;

    /**
     * 品质名称
     */
    @Schema(description = "品质名称")
    private String rarityName;

    /**
     * 品质颜色
     */
    @Schema(description = "品质颜色")
    private String rarityColor;

    /**
     * 价格
     */
    @Schema(description = "价格")
    private BigDecimal price;

    /**
     * 人名币价格
     */
    @Schema(description = "人名币价格")
    private BigDecimal cnyPrice;

    /**
     * 图片地址
     */
    @Schema(description = "图片地址")
    private String imageUrl;

    /**
     * 商品数量
     */
    @Schema(description = "商品数量")
    private Integer quantity;

    /**
     * 状态:1=启动,0=禁用
     */
    @Schema(description = "状态:1=启动,0=禁用")
    private Boolean status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 编辑时间
     */
    @Schema(description = "编辑时间")
    private LocalDateTime editTime;

}
