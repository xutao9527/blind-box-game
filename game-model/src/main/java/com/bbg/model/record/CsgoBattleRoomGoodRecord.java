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
 * 对战奖励装备记录 实体类。
 *
 * @author bbg
 * @since 2024-06-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "对战奖励装备记录")
@Table("csgo_battle_room_good")
public class CsgoBattleRoomGoodRecord extends BaseModel implements Serializable {

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
    private Long roomId;

    /**
     * Roll用户
     */
    @Schema(description = "Roll用户")
    private Long rollUserId;

    /**
     * 装备归属用户
     */
    @Schema(description = "装备归属用户")
    private Long luckUserId;

    /**
     * Roll回合
     */
    @Schema(description = "Roll回合")
    private Integer round;

    /**
     * Roll回合点数
     */
    @Schema(description = "Roll回合点数")
    private Integer roundNumber;

    /**
     * Roll盲盒编号
     */
    @Schema(description = "Roll盲盒编号")
    private Long boxId;

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
