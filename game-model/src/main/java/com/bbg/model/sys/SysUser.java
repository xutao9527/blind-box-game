package com.bbg.model.sys;

import com.bbg.model.record.SysUserRecord;

import com.mybatisflex.annotation.Column;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统用户")
public class SysUser extends SysUserRecord {
    @Column(ignore = true)
    private boolean superTenant = false;
}