package com.bbg.model.biz;

import com.bbg.model.record.BizDictRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统字典")
public class BizDict extends BizDictRecord {

}