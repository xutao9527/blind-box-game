package com.bbg.model.csgo;

import com.bbg.model.record.CsgoRollUserRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Roll房间用户")
public class CsgoRollUser extends CsgoRollUserRecord {

}