package com.bbg.admin.service.impl.biz;

import cn.hutool.core.util.IdUtil;
import com.bbg.admin.service.csgo.CsgoCapitalRecordService;
import com.bbg.admin.service.csgo.CsgoUserInfoService;
import com.bbg.core.constants.ServicesConst;
import com.bbg.core.utils.FairFactory;
import com.bbg.model.csgo.CsgoCapitalRecord;
import com.bbg.model.csgo.CsgoUserInfo;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizUser;
import com.bbg.admin.mapper.biz.BizUserMapper;
import com.bbg.admin.service.biz.BizUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务用户 服务层实现。
 *
 * @author bbg
 * @since 2024-04-25
 */
@Service
@RequiredArgsConstructor
public class BizUserServiceImpl extends ServiceImpl<BizUserMapper, BizUser> implements BizUserService {

    public final CsgoCapitalRecordService csgoCapitalRecordService;
    public final CsgoUserInfoService csgoUserInfoService;

    @Transactional(rollbackFor = Exception.class)
    public BizUser updateUserMoney(BizUser bizUser, CsgoCapitalRecord capitalRecord) {
        // 变更用户金额
        BizUser updateUser = UpdateEntity.of(BizUser.class, bizUser.getId());
        UpdateWrapper<BizUser> updateWrapper = UpdateWrapper.of(updateUser);
        String fieldName = QueryMethods.column(BizUser::getMoney).getName();
        String operation = capitalRecord.getChangeMoney().signum() == 1 ? "+" : "-";
        String updateMoney = "%s %s %.2f".formatted(fieldName, operation, capitalRecord.getChangeMoney().abs());
        updateWrapper.setRaw(BizUser::getMoney, updateMoney);
        getMapper().update(updateUser);
        // 查询最新的用户信息
        BizUser newUser =  getMapper().selectOneWithRelationsById(updateUser.getId());
        // 补资金流水
        capitalRecord.setAfterMoney(newUser.getMoney());
        capitalRecord.setBeforeMoney(bizUser.getMoney());
        csgoCapitalRecordService.save(capitalRecord);
        return newUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(BizUser entity) {
        entity.setId(IdUtil.getSnowflake(ServicesConst.ADMIN_SERVER.ordinal()).nextId());
        entity.setMoney(null);
        getMapper().insert(entity,true);
        FairFactory.FairEntity fairEntity = FairFactory.build();
        CsgoUserInfo csgoUserInfo = new CsgoUserInfo();
        csgoUserInfo.setUserId(entity.getId())
                .setSecretHash(fairEntity.getSecretHash())
                .setSecretSalt(fairEntity.getSecretSalt())
                .setPublicHash(fairEntity.getPublicHash())
                .setClientSeed(fairEntity.getClientSeed())
                .setRoundNumber(1);
        csgoUserInfoService.save(csgoUserInfo);
        return true;
    }
}
