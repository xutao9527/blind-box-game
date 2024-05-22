package com.bbg.core.service.biz;

import com.mybatisflex.core.service.IService;
import com.bbg.model.biz.BizDictDetail;

import java.io.Serializable;

/**
 * 系统字典详情 服务层。
 *
 * @author bbg
 * @since 2024-05-22
 */
public interface BizDictDetailService extends IService<BizDictDetail> {
    boolean save(BizDictDetail entity);

    boolean removeById(Serializable id);

    boolean updateById(BizDictDetail entity);
}
