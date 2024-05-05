package com.bbg.model.biz;


import com.bbg.model.csgo.CsgoUserInfo;
import com.bbg.model.record.BizUserRecord;
import com.mybatisflex.annotation.RelationOneToOne;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "业务用户")
public class BizUser extends BizUserRecord {

    @RelationOneToOne(selfField = "id",targetField = "userId")
    private CsgoUserInfo csgoUserInfo;

}