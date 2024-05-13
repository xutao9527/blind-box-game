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
 * 系统菜单 实体类。
 *
 * @author bbg
 * @since 2024-05-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统菜单")
@Table("sys_menu")
public class SysMenuRecord extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @Schema(description = "主键")
    private Long id;

    /**
     * 父Id
     */
    @Schema(description = "父Id")
    private Long parentId;

    /**
     * 菜单标题
     */
    @Schema(description = "菜单标题")
    private String title;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String name;

    /**
     * 请求路径
     */
    @Schema(description = "请求路径")
    private String path;

    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String component;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 显示:1=菜单,2=按钮
     */
    @Schema(description = "显示:1=菜单,2=按钮")
    private String type;

    /**
     * 显示:1=显示,0=隐藏
     */
    @Schema(description = "显示:1=显示,0=隐藏")
    private Boolean view;

    /**
     * 启用:1=启动,0=禁用
     */
    @Schema(description = "启用:1=启动,0=禁用")
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
