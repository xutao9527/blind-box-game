package com.bbg.model.sys;

import com.bbg.model.record.SysTenantRecord;

import com.mybatisflex.annotation.Column;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统租户")
public class SysTenant extends SysTenantRecord {
    /**
     * 父级标题
     */
    @Column(ignore = true)
    private String parentTenantName;
}