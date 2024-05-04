package com.bbg.model.biz;

import com.bbg.model.record.BizUserRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "业务用户")
public class BizUser extends BizUserRecord {

}