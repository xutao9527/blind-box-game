package com.bbg.box.service.biz;

import com.bbg.model.csgo.CsgoCapitalRecord;
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
    // 根据手机查询用户信息,取消脱敏
    BizUser getOneByMobile(String mobile);
    // 更新用户金额
    BizUser updateUserMoney(BizUser bizUser, CsgoCapitalRecord capitalRecord);
}
