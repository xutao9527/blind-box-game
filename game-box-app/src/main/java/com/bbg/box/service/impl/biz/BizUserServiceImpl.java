package com.bbg.box.service.impl.biz;

import com.bbg.box.service.csgo.CsgoCapitalRecordService;
import com.bbg.model.csgo.CsgoCapitalRecord;
import com.mybatisflex.core.mask.MaskManager;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizUser;
import com.bbg.box.mapper.biz.BizUserMapper;
import com.bbg.box.service.biz.BizUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务用户 服务层实现。
 *
 * @author bbg
 * @since 2024-05-03
 */
@Service
public class BizUserServiceImpl extends ServiceImpl<BizUserMapper, BizUser> implements BizUserService {

    @Autowired
    CsgoCapitalRecordService csgoCapitalRecordService;

    public BizUser getOneByMobile(String mobile){
        QueryWrapper queryWrapper = QueryWrapper.create(new BizUser().setMobile(mobile));
        return MaskManager.execWithoutMask(() -> getMapper().selectOneWithRelationsByQuery(queryWrapper));
    }

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
}
