package com.bbg.model.sys;

import com.bbg.model.record.SysUserRecord;

import com.mybatisflex.annotation.ColumnMask;
import com.mybatisflex.core.mask.Masks;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Schema(description = "系统用户")
public class SysUser extends SysUserRecord {
    /**
     * 密码
     */
    @Schema(description = "密码")
    @ColumnMask(Masks.PASSWORD)
    private String password;
}