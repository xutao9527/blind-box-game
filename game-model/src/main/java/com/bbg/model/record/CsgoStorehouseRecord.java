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
 * 装备仓库 实体类。
 *
 * @author bbg
 * @since 2024-05-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "装备仓库")
@Table("csgo_storehouse")
public class CsgoStorehouseRecord extends BaseModel implements Serializable {

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
     * 商品编号
     */
    @Schema(description = "商品编号")
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
     * 商品价值
     */
    @Schema(description = "商品价值")
    private BigDecimal price;

    /**
     * 来源类型
     */
    @Schema(description = "来源类型")
    private String sourceType;

    /**
     * 来源编号
     */
    @Schema(description = "来源编号")
    private Long sourceId;

    /**
     * 来源信息
     */
    @Schema(description = "来源信息")
    private String sourceInfo;

    /**
     * 商品状态
     */
    @Schema(description = "商品状态")
    private String status;

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
