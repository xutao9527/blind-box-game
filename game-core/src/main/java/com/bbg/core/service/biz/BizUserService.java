package com.bbg.core.service.biz;

import com.bbg.core.box.dto.LoginDto;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.csgo.CsgoCapitalRecord;
import com.mybatisflex.core.service.IService;
import com.bbg.model.biz.BizUser;

import java.util.List;

/**
 * 业务用户 服务层。
 *
 * @author bbg
 * @since 2024-05-22
 */
public interface BizUserService extends IService<BizUser> {

    /**
     * 根据手机查询用户信息,取消脱敏
     */
    BizUser getOneByMobile(String mobile);

    /**
     * 更新用户金额
     */
    BizUser updateUserMoney(BizUser bizUser, CsgoCapitalRecord capitalRecord);

    /**
     * 不同用户类型的人数
     */
    long getUserTypeCount(String userType);

    /**
     * 不同用户类型的用户Id集合
     */
    List<Long> getUserIdsByType(String userType);

    /**
     * 注册用户
     */
    ApiRet<String> register(LoginDto.RegisterReq registerReq);

    /**
     * 新增用户
     */
    void addUser(BizUser entity);

    /**
     * 批量新增虚拟用户
     */
    boolean addVirtualUser(int count);


}
