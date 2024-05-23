package com.bbg.model.biz;


import com.bbg.model.csgo.CsgoUserInfo;
import com.bbg.model.record.BizUserRecord;
import com.mybatisflex.annotation.ColumnMask;
import com.mybatisflex.annotation.RelationOneToOne;
import com.mybatisflex.core.mask.Masks;
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

    @Schema(description = "密码")
    @ColumnMask(Masks.PASSWORD)
    private String password;

    @Schema(description = "手机号")
    @ColumnMask(Masks.MOBILE)
    private String mobile;
}