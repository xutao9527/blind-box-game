package com.bbg.core.service.impl.biz;

import cn.hutool.core.util.IdUtil;
import com.bbg.core.annotation.RedisCache;
import com.bbg.core.constrans.KeyConst;
import com.bbg.core.constrans.ServicesConst;
import com.bbg.core.mapper.csgo.CsgoUserInfoMapper;
import com.bbg.core.service.RedisService;
import com.bbg.core.service.biz.BizDataService;
import com.bbg.core.service.biz.BizDictService;
import com.bbg.core.service.csgo.CsgoCapitalRecordService;
import com.bbg.core.service.csgo.CsgoUserInfoService;
import com.bbg.core.utils.FairFactory;
import com.bbg.core.utils.IdTool;
import com.bbg.model.biz.BizData;
import com.bbg.model.biz.BizDict;
import com.bbg.model.csgo.CsgoBox;
import com.bbg.model.csgo.CsgoCapitalRecord;
import com.bbg.model.csgo.CsgoUserInfo;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.mask.MaskManager;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizUser;
import com.bbg.core.mapper.biz.BizUserMapper;
import com.bbg.core.service.biz.BizUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 业务用户 服务层实现。
 *
 * @author bbg
 * @since 2024-05-22
 */
@Service
@RequiredArgsConstructor
public class BizUserServiceImpl extends ServiceImpl<BizUserMapper, BizUser> implements BizUserService {
    public final CsgoCapitalRecordService csgoCapitalRecordService;
    public final CsgoUserInfoService csgoUserInfoService;
    public final BizDictService bizDictService;
    public final BizDataService dataService;
    public final RedisService redisService;

    @Override
    public BizUser getById(Serializable id) {
        return getMapper().selectOneWithRelationsById(id);
        // return MaskManager.execWithoutMask(() -> getMapper().selectOneWithRelationsById(id));
    }

    /**
     * 根据手机查询用户信息,取消脱敏
     */
    public BizUser getOneByMobile(String mobile) {
        QueryWrapper queryWrapper = QueryWrapper.create(new BizUser().setMobile(mobile));
        return MaskManager.execWithoutMask(() -> getMapper().selectOneWithRelationsByQuery(queryWrapper));
    }

    /**
     * 更新用户金额
     */
    @Transactional(rollbackFor = Exception.class)
    public BizUser updateUserMoney(BizUser bizUser, CsgoCapitalRecord capitalRecord) {
        BizDict userTypeDict = bizDictService.getDictByTag("user_type");
        // 如果不是真实用户或者测试用户，直接返回,不扣钱
        if(!bizUser.getType().equals(userTypeDict.getValueByAlias("real_user")) || !bizUser.getType().equals(userTypeDict.getValueByAlias("test_user"))){
           return bizUser;
        }
        // 变更用户金额
        BizUser updateUser = UpdateEntity.of(BizUser.class, bizUser.getId());
        UpdateWrapper<BizUser> updateWrapper = UpdateWrapper.of(updateUser);
        String fieldName = QueryMethods.column(BizUser::getMoney).getName();
        String operation = capitalRecord.getChangeMoney().signum() == 1 ? "+" : "-";
        String updateMoney = "%s %s %.2f".formatted(fieldName, operation, capitalRecord.getChangeMoney().abs());
        updateWrapper.setRaw(BizUser::getMoney, updateMoney);
        getMapper().update(updateUser);
        // 查询最新的用户信息
        BizUser newUser = getMapper().selectOneWithRelationsById(updateUser.getId());
        // 补资金流水
        capitalRecord.setMobile(bizUser.getMobile());
        capitalRecord.setNickName(bizUser.getNickName());
        capitalRecord.setAfterMoney(newUser.getMoney());
        capitalRecord.setBeforeMoney(bizUser.getMoney());
        csgoCapitalRecordService.save(capitalRecord);
        // 跟新redis缓存
        redisService.updateUser(bizUser);
        return newUser;
    }

    /**
     * 不同用户类型的人数
     */
    @RedisCache(value = "#userType", key = KeyConst.USER_TYPE_COUNT, liveTime = 5 * 60, timeUnit = TimeUnit.SECONDS)
    public long getUserTypeCount(String userType) {
        QueryWrapper queryWrapper = QueryWrapper.create(new BizUser().setType(userType));
        return getMapper().selectCountByQuery(queryWrapper);
    }

    /**
     * 不同用户类型的用户Id集合
     */
    public List<Long> getUserIdsByType(String userType) {
        QueryWrapper queryWrapper = QueryWrapper.create().select(QueryMethods.column(BizUser::getId)).eq(BizUser::getType, userType);
        return getMapper().selectListByQueryAs(queryWrapper, Long.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(BizUser entity) {
        entity.setId(IdTool.nextId());
        getMapper().insert(entity, true);
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

    /**
     * 批量新增虚拟用户
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addVirtualUser(int count) {
        var userTypeDict = bizDictService.getDictByTag("user_type");
        var dataTypeDict = bizDictService.getDictByTag("biz_data_type");
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(QueryMethods.column(BizData::getValue))
                .from(BizData.class).eq(BizData::getType,dataTypeDict.getValueByAlias("nick_name"));
        List<String> nickNameList = dataService.listAs(queryWrapper, String.class);
        List<BizUser> userList = new ArrayList<>();
        List<CsgoUserInfo> userInfoList = new ArrayList<>();
        if (!nickNameList.isEmpty()) {
            final var secureRandom = new SecureRandom();
            for (int i = 0; i < count; i++) {
                BizUser bizUser = new BizUser();
                bizUser.setId(IdTool.nextId());
                bizUser.setNickName(nickNameList.get(secureRandom.nextInt(nickNameList.size())));
                bizUser.setType(userTypeDict.getValueByAlias("virtual_user"));
                bizUser.setEnable(true);
                userList.add(bizUser);
                FairFactory.FairEntity fairEntity = FairFactory.build();
                CsgoUserInfo csgoUserInfo = new CsgoUserInfo();
                csgoUserInfo.setId(IdTool.nextId()).setUserId(bizUser.getId())
                        .setSecretHash(fairEntity.getSecretHash())
                        .setSecretSalt(fairEntity.getSecretSalt())
                        .setPublicHash(fairEntity.getPublicHash())
                        .setClientSeed(fairEntity.getClientSeed())
                        .setRoundNumber(1);
                userInfoList.add(csgoUserInfo);
            }
        }
        Db.executeBatch(userList, BizUserMapper.class,
                (mapper, user) -> {
                    mapper.insertWithPk(user, true);
                });
        Db.executeBatch(userInfoList, CsgoUserInfoMapper.class,
                (mapper, userInfo) -> {
                    mapper.insertWithPk(userInfo, true);
                });
        return true;
    }
}
