package com.bbg.model.sys;

import com.bbg.model.record.SysUserRoleRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户-角色-中间")
public class SysUserRole extends SysUserRoleRecord {

}