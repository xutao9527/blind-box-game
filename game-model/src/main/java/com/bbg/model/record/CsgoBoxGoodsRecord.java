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
 * CSGO箱子商品 实体类。
 *
 * @author bbg
 * @since 2024-05-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "CSGO箱子商品")
@Table("csgo_box_goods")
public class CsgoBoxGoodsRecord extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @Schema(description = "主键")
    private Long id;

    /**
     * 游戏箱子
     */
    @Schema(description = "游戏箱子")
    private Long boxId;

    /**
     * 游戏商品
     */
    @Schema(description = "游戏商品")
    private Long goodId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String name;

    /**
     * 商品别名
     */
    @Schema(description = "商品别名")
    private String nameAlias;

    /**
     * 图片地址
     */
    @Schema(description = "图片地址")
    private String imageUrl;

    /**
     * 商品价格
     */
    @Schema(description = "商品价格")
    private BigDecimal price;

    /**
     * 获得概率
     */
    @Schema(description = "获得概率")
    private BigDecimal rate;

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

}
