package com.bbg.model.record;

import com.bbg.model.base.BaseModel;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 对战房间箱子 实体类。
 *
 * @author bbg
 * @since 2024-05-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "对战房间箱子")
@Table("csgo_battle_room_box")
public class CsgoBattleRoomBoxRecord extends BaseModel implements Serializable {

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
     * 箱子编号
     */
    @Schema(description = "箱子编号")
    private Long boxId;

    /**
     * 箱子名称
     */
    @Schema(description = "箱子名称")
    private String name;

    /**
     * 箱子别名
     */
    @Schema(description = "箱子别名")
    private String nameAlias;

    /**
     * 箱子图片
     */
    @Schema(description = "箱子图片")
    private String imageUrl;

}
