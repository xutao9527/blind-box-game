package com.bbg.model.csgo;

import com.bbg.model.record.CsgoRobotRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "机器人")
public class CsgoRobot extends CsgoRobotRecord {

}