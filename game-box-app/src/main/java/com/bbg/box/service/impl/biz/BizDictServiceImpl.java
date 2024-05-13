package com.bbg.box.service.impl.biz;

import com.bbg.core.annotation.RedisCache;
import com.bbg.core.constants.KeyConst;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizDict;
import com.bbg.box.mapper.biz.BizDictMapper;
import com.bbg.box.service.biz.BizDictService;
import org.springframework.stereotype.Service;

/**
 * 系统字典 服务层实现。
 *
 * @author bbg
 * @since 2024-05-03
 */
@Service
public class BizDictServiceImpl extends ServiceImpl<BizDictMapper, BizDict> implements BizDictService {

    @RedisCache(value = "#tag", key = KeyConst.DICT_TAG)
    public BizDict getDictByTag(String tag) {
        QueryWrapper queryWrapper = QueryWrapper.create().and(BizDict::getTag).eq(tag);
        return getMapper().selectOneWithRelationsByQuery(queryWrapper);
    }
}
