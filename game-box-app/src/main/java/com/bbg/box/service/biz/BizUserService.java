package com.bbg.box.service.biz;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.bbg.model.biz.BizUser;

/**
 * 业务用户 服务层。
 *
 * @author bbg
 * @since 2024-05-03
 */
public interface BizUserService extends IService<BizUser> {
    BizUser getOneWithRelations(QueryWrapper query);
}
