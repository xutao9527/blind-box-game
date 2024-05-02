package com.bbg.model.biz;

import com.bbg.model.record.BizDictDetailRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Schema(description = "系统字典详情")
public class BizDictDetail extends BizDictDetailRecord {

}