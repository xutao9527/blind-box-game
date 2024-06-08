package com.bbg.model.record;

import com.bbg.model.base.BaseModel;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 撸房装备 实体类。
 *
 * @author bbg
 * @since 2024-06-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "撸房装备")
@Table("csgo_roll_good")
public class CsgoRollGoodRecord extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Id
    @Schema(description = "编号")
    private Long id;

    /**
     * 房间编号
     */
    @Schema(description = "房间编号")
    private Long rollId;

    /**
     * 装备归属用户
     */
    @Schema(description = "装备归属用户")
    private Long luckUserId;

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
     * 商品价格
     */
    @Schema(description = "商品价格")
    private BigDecimal goodPrice;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String goodImage;

}
