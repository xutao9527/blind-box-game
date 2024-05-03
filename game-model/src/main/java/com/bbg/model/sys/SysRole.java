package com.bbg.model.sys;

import com.bbg.model.record.SysRoleRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统角色")
public class SysRole extends SysRoleRecord {

}