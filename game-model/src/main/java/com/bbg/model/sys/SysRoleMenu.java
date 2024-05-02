package com.bbg.model.sys;

import com.bbg.model.record.SysRoleMenuRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Schema(description = "角色-菜单-中间")
public class SysRoleMenu extends SysRoleMenuRecord {

}