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
 * CSGO箱子 实体类。
 *
 * @author bbg
 * @since 2024-06-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "CSGO箱子")
@Table("csgo_box")
public class CsgoBoxRecord extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @Schema(description = "主键")
    private Long id;

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
     * 图片地址
     */
    @Schema(description = "图片地址")
    private String imageUrl;

    /**
     * 箱子类型
     */
    @Schema(description = "箱子类型")
    private String type;

    /**
     * 箱子分组
     */
    @Schema(description = "箱子分组")
    private String group;

    /**
     * 箱子价格
     */
    @Schema(description = "箱子价格")
    private BigDecimal price;

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
