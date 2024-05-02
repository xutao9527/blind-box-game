package com.bbg.model.record;

import com.bbg.model.base.BaseModel;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 角色-菜单-中间 实体类。
 *
 * @author bbg
 * @since 2024-05-01
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色-菜单-中间")
@Table(value = "sys_role_menu")
public class SysRoleMenuRecord extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    @Id
    @Schema(description = "角色编号")
    private Long roleId;

    /**
     * 菜单编号
     */
    @Id
    @Schema(description = "菜单编号")
    private Long menuId;

}
