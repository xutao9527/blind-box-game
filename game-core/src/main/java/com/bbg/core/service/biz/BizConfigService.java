package com.bbg.core.service.biz;

import com.mybatisflex.core.service.IService;
import com.bbg.model.biz.BizConfig;

import java.io.Serializable;

/**
 * 平台配置 服务层。
 *
 * @author bbg
 * @since 2024-06-08
 */
public interface BizConfigService extends IService<BizConfig> {
    BizConfig getConfigByNameAlias(String nameAlias);

    boolean removeById(Serializable id);

    boolean updateById(BizConfig entity);
}
