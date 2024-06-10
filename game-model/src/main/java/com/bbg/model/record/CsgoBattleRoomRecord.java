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
 * 对战房间 实体类。
 *
 * @author bbg
 * @since 2024-06-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "对战房间")
@Table("csgo_battle_room")
public class CsgoBattleRoomRecord extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Id
    @Schema(description = "编号")
    private Long id;

    /**
     * 创建用户
     */
    @Schema(description = "创建用户")
    private Long createUserId;

    /**
     * 对战模式
     */
    @Schema(description = "对战模式")
    private String battleModel;

    /**
     * 房间人数
     */
    @Schema(description = "房间人数")
    private Integer peopleNumber;

    /**
     * 房间价格
     */
    @Schema(description = "房间价格")
    private BigDecimal roomPrice;

    /**
     * 房间状态
     */
    @Schema(description = "房间状态")
    private String status;

    /**
     * 秘密哈希
     */
    @Schema(description = "秘密哈希")
    private String secretHash;

    /**
     * 秘密盐值
     */
    @Schema(description = "秘密盐值")
    private String secretSalt;

    /**
     * 公共哈希
     */
    @Schema(description = "公共哈希")
    private String publicHash;

    /**
     * 客户端种子
     */
    @Schema(description = "客户端种子")
    private String clientSeed;

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
