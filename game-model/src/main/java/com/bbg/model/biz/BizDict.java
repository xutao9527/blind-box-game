package com.bbg.model.biz;

import com.bbg.model.record.BizDictRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Schema(description = "系统字典")
public class BizDict extends BizDictRecord {

}