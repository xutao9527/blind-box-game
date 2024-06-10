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
 * 撸房 实体类。
 *
 * @author bbg
 * @since 2024-06-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "撸房")
@Table("csgo_roll")
public class CsgoRollRecord extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Id
    @Schema(description = "编号")
    private Long id;

    /**
     * 创建用户编号
     */
    @Schema(description = "创建用户编号")
    private Long createUserId;

    /**
     * 创建用户名称
     */
    @Schema(description = "创建用户名称")
    private String createUserName;

    /**
     * 创建用户头像
     */
    @Schema(description = "创建用户头像")
    private String createUserPhoto;

    /**
     * 房间标题
     */
    @Schema(description = "房间标题")
    private String rollTitle;

    /**
     * 房间描述
     */
    @Schema(description = "房间描述")
    private String rollRemark;

    /**
     * 撸房类型
     */
    @Schema(description = "撸房类型")
    private String rollType;

    /**
     * 撸房模式
     */
    @Schema(description = "撸房模式")
    private String rollModel;

    /**
     * 房间开始时间
     */
    @Schema(description = "房间开始时间")
    private LocalDateTime startTime;

    /**
     * 房间结束时间
     */
    @Schema(description = "房间结束时间")
    private LocalDateTime endTime;

    /**
     * 房间人数
     */
    @Schema(description = "房间人数")
    private Integer peopleNumber;

    /**
     * 加入房间条件
     */
    @Schema(description = "加入房间条件")
    private String joinCondition;

    /**
     * 加入房间密码
     */
    @Schema(description = "加入房间密码")
    private String joinCode;

    /**
     * 房间排序
     */
    @Schema(description = "房间排序")
    private Integer sort;

    /**
     * 房间状态
     */
    @Schema(description = "房间状态")
    private String status;

    /**
     * 状态:启用与禁用
     */
    @Schema(description = "状态:启用与禁用")
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
