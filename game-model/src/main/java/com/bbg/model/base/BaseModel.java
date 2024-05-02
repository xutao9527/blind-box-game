package com.bbg.model.base;

import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
public class BaseModel {
    /**
     * 扩展参数
     */
    @Column(ignore = true)
    @Schema(description = "扩展参数")
    private Map<String,Object> expandProps;
}
