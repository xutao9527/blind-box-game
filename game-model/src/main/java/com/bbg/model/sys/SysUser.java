package com.bbg.model.sys;

import com.bbg.model.record.SysUserRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Schema(description = "系统用户")
public class SysUser extends SysUserRecord {

}