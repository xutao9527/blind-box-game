package com.bbg.model.sys;

import com.bbg.model.record.SysMenuRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Schema(description = "系统菜单")
public class SysMenu extends SysMenuRecord {

}