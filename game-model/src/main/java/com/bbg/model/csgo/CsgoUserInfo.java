package com.bbg.model.csgo;

import com.bbg.model.record.CsgoUserInfoRecord;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "CSGO用户信息")
public class CsgoUserInfo extends CsgoUserInfoRecord {

}