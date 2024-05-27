package com.bbg.core.service.csgo;

import com.mybatisflex.core.service.IService;
import com.bbg.model.csgo.CsgoConfig;

import java.io.Serializable;

/**
 * 游戏配置 服务层。
 *
 * @author bbg
 * @since 2024-05-22
 */
public interface CsgoConfigService extends IService<CsgoConfig> {
    CsgoConfig getConfigByNameAlias(String nameAlias);

    boolean removeById(Serializable id);

    boolean updateById(CsgoConfig entity);
}
