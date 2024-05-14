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
 * 资金流水 实体类。
 *
 * @author bbg
 * @since 2024-05-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "资金流水")
@Table("csgo_capital_record")
public class CsgoCapitalRecordRecord extends BaseModel implements Serializable {

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
     * 变更金额
     */
    @Schema(description = "变更金额")
    private BigDecimal changeMoney;

    /**
     * 变更前金额
     */
    @Schema(description = "变更前金额")
    private BigDecimal beforeMoney;

    /**
     * 变更后金额
     */
    @Schema(description = "变更后金额")
    private BigDecimal afterMoney;

    /**
     * 变更类型
     */
    @Schema(description = "变更类型")
    private String type;

    /**
     * 变更来源
     */
    @Schema(description = "变更来源")
    private String sourceId;

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
