package com.bbg.model.biz;

import com.bbg.model.record.BizChannelRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "渠道管理")
public class BizChannel extends BizChannelRecord {

}