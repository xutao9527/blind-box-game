package com.bbg.admin.service.impl.csgo;

import com.bbg.admin.service.biz.BizDictService;
import com.bbg.model.biz.BizDict;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoRoll;
import com.bbg.admin.mapper.csgo.CsgoRollMapper;
import com.bbg.admin.service.csgo.CsgoRollService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Roll房间 服务层实现。
 *
 * @author bbg
 * @since 2024-05-16
 */
@Service
@RequiredArgsConstructor
public class CsgoRollServiceImpl extends ServiceImpl<CsgoRollMapper, CsgoRoll> implements CsgoRollService {

    public final BizDictService bizDictService;

    @Override
    public boolean save(CsgoRoll entity) {
        BizDict rollStatusDict = bizDictService.getDictByTag("csgo_roll_status");
        entity.setStatus(rollStatusDict.getValueByAlias("roll_wait_online"));
        return super.save(entity);
    }
}
