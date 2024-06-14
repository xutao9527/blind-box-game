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
 * 游戏配置 实体类。
 *
 * @author bbg
 * @since 2024-06-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "游戏配置")
@Table("csgo_config")
public class CsgoConfigRecord extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @Schema(description = "主键")
    private Long id;

    /**
     * 配置名称
     */
    @Schema(description = "配置名称")
    private String name;

    /**
     * 配置别名
     */
    @Schema(description = "配置别名")
    private String nameAlias;

    /**
     * 配置值
     */
    @Schema(description = "配置值")
    private String value;

    /**
     * 配置描述
     */
    @Schema(description = "配置描述")
    private String remark;

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
