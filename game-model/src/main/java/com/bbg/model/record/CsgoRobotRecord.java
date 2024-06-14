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
 * 机器人 实体类。
 *
 * @author bbg
 * @since 2024-06-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "机器人")
@Table("csgo_robot")
public class CsgoRobotRecord extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Id
    @Schema(description = "编号")
    private Long id;

    /**
     * 机器人名称
     */
    @Schema(description = "机器人名称")
    private String name;

    /**
     * 机器人别名
     */
    @Schema(description = "机器人别名")
    private String nameAlias;

    /**
     * 机器人头像
     */
    @Schema(description = "机器人头像")
    private String imageUrl;

    /**
     * 参战总数
     */
    @Schema(description = "参战总数")
    private Long takeTotal;

    /**
     * 赢的总数
     */
    @Schema(description = "赢的总数")
    private Long winTotal;

    /**
     * 启动状态
     */
    @Schema(description = "启动状态")
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
