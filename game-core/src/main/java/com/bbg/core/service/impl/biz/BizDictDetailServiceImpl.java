package com.bbg.core.service.impl.biz;

import com.bbg.core.service.RedisService;
import com.bbg.core.constrans.KeyConst;
import com.bbg.core.service.biz.BizDictService;
import com.bbg.model.biz.BizDict;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizDictDetail;
import com.bbg.core.mapper.biz.BizDictDetailMapper;
import com.bbg.core.service.biz.BizDictDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 系统字典详情 服务层实现。
 *
 * @author bbg
 * @since 2024-05-22
 */
@Service
@RequiredArgsConstructor
public class BizDictDetailServiceImpl extends ServiceImpl<BizDictDetailMapper, BizDictDetail> implements BizDictDetailService {
    public final RedisService redisService;
    public final BizDictService bizDictService;

    @Override
    public boolean save(BizDictDetail entity){
        BizDict bizDict = bizDictService.getById(entity.getDictId());
        if (bizDict != null) {
            redisService.delete(KeyConst.build(KeyConst.DICT_TAG, bizDict.getTag()));
        }
        return this.getMapper().insert(entity,true) > 0;
    }

    @Override
    public boolean removeById(Serializable id){
        BizDictDetail bizDictDetail = getMapper().selectOneById(id);
        if(bizDictDetail!=null){
            BizDict bizDict = bizDictService.getById(bizDictDetail.getDictId());
            if (bizDict != null) {
                redisService.delete(KeyConst.build(KeyConst.DICT_TAG, bizDict.getTag()));
            }
            return getMapper().deleteById(id) > 0;
        }
        return false;

    }

    @Override
    public boolean updateById(BizDictDetail entity){
        BizDict bizDict = bizDictService.getById(entity.getDictId());
        if (bizDict != null) {
            redisService.delete(KeyConst.build(KeyConst.DICT_TAG, bizDict.getTag()));
        }
        return getMapper().update(entity) > 0;
    }
}
