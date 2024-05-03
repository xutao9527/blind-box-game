package com.bbg.model.biz;

import com.bbg.model.record.BizDictDetailRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统字典详情")
public class BizDictDetail extends BizDictDetailRecord {

}