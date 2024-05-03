package com.bbg.model.sys;

import com.bbg.model.record.SysMenuRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统菜单")
public class SysMenu extends SysMenuRecord {

}