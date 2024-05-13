package com.bbg.model.biz;

import com.bbg.model.csgo.CsgoBoxGoods;
import com.bbg.model.record.BizDictRecord;

import com.mybatisflex.annotation.RelationOneToMany;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统字典")
public class BizDict extends BizDictRecord {

    @RelationOneToMany(selfField = "id", targetField = "dictId", orderBy = "sort")
    private List<BizDictDetail> bizDictDetails;

    public String getValueByAlias(String alias){
        String value = null;
        if(bizDictDetails!=null){
            BizDictDetail dictDetail = bizDictDetails.stream().filter(detail -> detail.getLabelAlias().equals(alias)).findFirst().orElse(null);
            if(dictDetail!=null){
                value = dictDetail.getValue();
            }
        }
        return value;
    }

}