package com.bbg.core.service.impl.csgo;

import com.bbg.core.annotation.RedisCache;
import com.bbg.core.annotation.RedisClear;
import com.bbg.core.constrans.KeyConst;
import com.bbg.core.service.RedisService;
import com.bbg.model.biz.BizDict;
import com.bbg.model.biz.BizDictDetail;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoConfig;
import com.bbg.core.mapper.csgo.CsgoConfigMapper;
import com.bbg.core.service.csgo.CsgoConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 游戏配置 服务层实现。
 *
 * @author bbg
 * @since 2024-05-22
 */
@Service
@RequiredArgsConstructor
public class CsgoConfigServiceImpl extends ServiceImpl<CsgoConfigMapper, CsgoConfig> implements CsgoConfigService {
    public final RedisService redisService;

    @Override
    @RedisCache(value = "#nameAlias", key = KeyConst.GAME_CONFIG_NAME_ALIAS)
    public CsgoConfig getConfigByNameAlias(String nameAlias) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .eq(CsgoConfig::getEnable, true)
                .eq(CsgoConfig::getNameAlias, nameAlias);
        return getOne(queryWrapper);
    }

    @Override
    public boolean removeById(Serializable id) {
        CsgoConfig csgoConfig = getMapper().selectOneById(id);
        if (csgoConfig != null) {
            redisService.delete(KeyConst.build(KeyConst.GAME_CONFIG_NAME_ALIAS, csgoConfig.getNameAlias()));
            return getMapper().deleteById(id) > 0;
        }
        return false;
    }

    @Override
    @RedisClear(value = "#entity.nameAlias", key = KeyConst.GAME_CONFIG_NAME_ALIAS)
    public boolean updateById(CsgoConfig entity) {
        CsgoConfig csgoConfig = getMapper().selectOneById(entity.getId());
        if (csgoConfig != null) {
            redisService.delete(KeyConst.build(KeyConst.GAME_CONFIG_NAME_ALIAS, csgoConfig.getNameAlias()));
            return this.updateById(entity, true);
        }
        return false;
    }
}
