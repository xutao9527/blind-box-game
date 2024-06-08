package com.bbg.core.service.impl.biz;

import com.bbg.core.annotation.RedisCache;
import com.bbg.core.annotation.RedisClear;
import com.bbg.core.constrans.KeyConst;
import com.bbg.core.service.RedisService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizConfig;
import com.bbg.core.mapper.biz.BizConfigMapper;
import com.bbg.core.service.biz.BizConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 平台配置 服务层实现。
 *
 * @author bbg
 * @since 2024-06-08
 */
@Service
@RequiredArgsConstructor
public class BizConfigServiceImpl extends ServiceImpl<BizConfigMapper, BizConfig> implements BizConfigService {
    public final RedisService redisService;

    @Override
    @RedisCache(value = "#nameAlias", key = KeyConst.BIZ_CONFIG_NAME_ALIAS)
    public BizConfig getConfigByNameAlias(String nameAlias) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .eq(BizConfig::getEnable, true)
                .eq(BizConfig::getNameAlias, nameAlias);
        return getOne(queryWrapper);
    }

    @Override
    public boolean removeById(Serializable id) {
        BizConfig bizConfig = getMapper().selectOneById(id);
        if (bizConfig != null) {
            redisService.delete(KeyConst.build(KeyConst.BIZ_CONFIG_NAME_ALIAS, bizConfig.getNameAlias()));
            return getMapper().deleteById(id) > 0;
        }
        return false;
    }

    @Override
    @RedisClear(value = "#entity.nameAlias", key = KeyConst.BIZ_CONFIG_NAME_ALIAS)
    public boolean updateById(BizConfig entity) {
        BizConfig bizConfig = getMapper().selectOneById(entity.getId());
        if (bizConfig != null) {
            redisService.delete(KeyConst.build(KeyConst.BIZ_CONFIG_NAME_ALIAS, bizConfig.getNameAlias()));
            return this.updateById(entity, true);
        }
        return false;
    }
}
