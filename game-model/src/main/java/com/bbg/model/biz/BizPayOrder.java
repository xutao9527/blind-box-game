package com.bbg.model.biz;

import com.bbg.model.record.BizPayOrderRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "支付订单")
public class BizPayOrder extends BizPayOrderRecord {

}