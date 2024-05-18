package com.bbg.box.service.impl.csgo;

import com.bbg.box.service.biz.BizDictService;
import com.bbg.core.box.dto.RollDto;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.biz.BizDict;
import com.bbg.model.csgo.CsgoBattleRoom;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoRoll;
import com.bbg.box.mapper.csgo.CsgoRollMapper;
import com.bbg.box.service.csgo.CsgoRollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Roll房间 服务层实现。
 *
 * @author bbg
 * @since 2024-05-16
 */
@Service
public class CsgoRollServiceImpl extends ServiceImpl<CsgoRollMapper, CsgoRoll> implements CsgoRollService {

    @Autowired
    BizDictService bizDictService;

    @Override
    public CsgoRoll getById(Serializable id) {
        return super.getById(id);
    }

    public ApiRet<CsgoRoll> join(Long rollId){
        CsgoRoll roll = null;

        return null;
    }

    public Page<CsgoRoll> getRollList(RollDto.GetRollListReq getRollListReq){
        BizDict rollStatusDict = bizDictService.getDictByTag("csgo_roll_status");
        rollStatusDict.getValueByAlias("roll_online");
        rollStatusDict.getValueByAlias("roll_offline");

        QueryWrapper queryWrapper = QueryWrapper.create()
                .in(CsgoRoll::getStatus,rollStatusDict.getValueByAlias("roll_online"),rollStatusDict.getValueByAlias("roll_offline"))
                .eq(CsgoRoll::getEnable,true);
        return page(Page.of(getRollListReq.getPageNumber(), getRollListReq.getPageSize()), queryWrapper);
    }
}
