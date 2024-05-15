package com.bbg.admin.service.biz;

import com.bbg.admin.service.csgo.CsgoUserInfoService;
import com.bbg.model.csgo.CsgoCapitalRecord;
import com.bbg.model.csgo.CsgoUserInfo;
import com.mybatisflex.core.service.IService;
import com.bbg.model.biz.BizUser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 业务用户 服务层。
 *
 * @author bbg
 * @since 2024-04-25
 */
public interface BizUserService extends IService<BizUser> {
    // 更新用户金额
    BizUser updateUserMoney(BizUser bizUser, CsgoCapitalRecord capitalRecord);


}
