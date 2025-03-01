package com.bbg.model.biz;

import com.bbg.model.record.BizPayPlatformRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "支付平台管理")
public class BizPayPlatform extends BizPayPlatformRecord {

}