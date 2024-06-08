package com.bbg.model.biz;

import com.bbg.model.record.BizConfigRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "平台配置")
public class BizConfig extends BizConfigRecord {

}