package com.bbg.core.service.impl.biz;

import com.bbg.core.annotation.RedisCache;
import com.bbg.core.service.RedisService;
import com.bbg.core.constrans.KeyConst;
import com.bbg.model.biz.BizDictDetail;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizDict;
import com.bbg.core.mapper.biz.BizDictMapper;
import com.bbg.core.service.biz.BizDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 系统字典 服务层实现。
 *
 * @author bbg
 * @since 2024-05-22
 */
@Service
@RequiredArgsConstructor
public class BizDictServiceImpl extends ServiceImpl<BizDictMapper, BizDict> implements BizDictService {
    public final RedisService redisService;

    @RedisCache(value = "#tag", key = KeyConst.DICT_TAG)
    public BizDict getDictByTag(String tag) {
        QueryWrapper queryWrapper = QueryWrapper.create().and(BizDict::getTag).eq(tag);
        return getMapper().selectOneWithRelationsByQuery(queryWrapper);
    }

    @Override
    public boolean removeById(Serializable id) {
        BizDict bizDict = getMapper().selectOneById(id);
        if (bizDict != null) {
            redisService.delete(KeyConst.build(KeyConst.DICT_TAG, bizDict.getTag()));
            getMapper().deleteByQuery(QueryWrapper.create(new BizDictDetail().setDictId(bizDict.getId())));
            return getMapper().deleteById(id) > 0;
        }
        return false;
    }

    @Override
    public boolean updateById(BizDict entity) {
        redisService.delete(KeyConst.build(KeyConst.DICT_TAG, entity.getTag()));        // 清缓存
        return this.updateById(entity, true);
    }
}
