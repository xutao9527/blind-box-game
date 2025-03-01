package com.bbg.core.service.impl.biz;

import cn.hutool.core.codec.Base58;
import cn.hutool.core.convert.Convert;
import com.bbg.core.annotation.RedisCache;
import com.bbg.core.box.dto.LoginDto;
import com.bbg.core.constrans.KeyConst;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.mapper.csgo.CsgoUserInfoMapper;
import com.bbg.core.service.RedisService;
import com.bbg.core.service.biz.BizChannelService;
import com.bbg.core.service.biz.BizDataService;
import com.bbg.core.service.biz.BizDictService;
import com.bbg.core.service.csgo.CsgoCapitalRecordService;
import com.bbg.core.service.csgo.CsgoUserInfoService;
import com.bbg.core.third.sms.SmsService;
import com.bbg.core.utils.FairFactory;
import com.bbg.core.utils.IdTool;
import com.bbg.model.biz.BizChannel;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.*;
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
    public final SmsService smsService;
    @Autowired
    private BizChannelService bizChannelService;

    @Override
    public BizUser getById(Serializable id) {
        return MaskManager.execWithoutMask(() -> getMapper().selectOneWithRelationsById(id));
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
        if (!bizUser.getType().equals(userTypeDict.getValueByAlias("real_user")) && !bizUser.getType().equals(userTypeDict.getValueByAlias("test_user"))) {
            return bizUser;
        } else {
            bizUser = getMapper().selectOneWithRelationsByQuery(QueryWrapper.create().eq(BizUser::getId, bizUser.getId()).forUpdate());
            if (capitalRecord.getChangeMoney().signum() != 1 && bizUser.getMoney().compareTo(capitalRecord.getChangeMoney().abs()) < 0) {
                throw new RuntimeException("用户余额不足");
            }
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
    @RedisCache(value = "#userType", key = KeyConst.USER_TYPE_COUNT, liveTime = 5 * 60, timeUnit = TimeUnit.SECONDS, tenantFlag = true)
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

    /**
     * 注册用户
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiRet<String> register(LoginDto.RegisterReq registerReq) {
        // 判断手机号是否已注册
        if (this.getOneByMobile(registerReq.getMobile()) != null) {
            return ApiRet.buildNo("手机号已注册");
        }
        // 查询邀请码与渠道码
        Map<String,String> ChannelMap = bizChannelService.getChannelCode(registerReq.getInvitationCode());
        // 验证短信验证码
        boolean isOk = smsService.verifySmsCode(registerReq.getMobile(), registerReq.getCode());
        if (!isOk) {
            return ApiRet.buildNo("验证码错误");
        }
        var userTypeDict = bizDictService.getDictByTag("user_type");
        var dataTypeDict = bizDictService.getDictByTag("biz_data_type");
        QueryWrapper nickNameQueryWrapper = QueryWrapper.create()
                .select(QueryMethods.column(BizData::getValue))
                .from(BizData.class).eq(BizData::getType, dataTypeDict.getValueByAlias("nick_name"));
        List<String> nickNameList = dataService.listAs(nickNameQueryWrapper, String.class);
        QueryWrapper photoQueryWrapper = QueryWrapper.create()
                .select(QueryMethods.column(BizData::getValue))
                .from(BizData.class).eq(BizData::getType, dataTypeDict.getValueByAlias("profile_photo"));
        List<String> profilePhotoList = dataService.listAs(photoQueryWrapper, String.class);
        final var secureRandom = new SecureRandom();
        // 保存用户信息
        BizUser bizUser = new BizUser();
        bizUser.setMobile(registerReq.getMobile());
        bizUser.setPassword(registerReq.getPassword());
        bizUser.setPhoto(profilePhotoList.get(secureRandom.nextInt(profilePhotoList.size())));
        bizUser.setNickName(nickNameList.get(secureRandom.nextInt(nickNameList.size())));
        bizUser.setType(userTypeDict.getValueByAlias("real_user"));
        bizUser.setEnable(true);
        bizUser.setMoney(BigDecimal.valueOf(0.0));
        bizUser.setChannelCode(ChannelMap.get("channelCode"));//设置渠道码
        bizUser.setInvitationCode(ChannelMap.get("invitationCode"));//设置邀请码
        this.addUser(bizUser);
        return ApiRet.buildOk("注册成功");
    }

    /**
     * 新增用户
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUser(BizUser entity) {
        entity.setId(IdTool.nextId());
        entity.setPromoCode(Base58.encode(Convert.longToBytes(entity.getId())));    //生成推广码
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
    }

    /**
     * 批量新增虚拟用户
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addVirtualUser(int count) {
        var userTypeDict = bizDictService.getDictByTag("user_type");
        var dataTypeDict = bizDictService.getDictByTag("biz_data_type");
        QueryWrapper nickNameQueryWrapper = QueryWrapper.create()
                .select(QueryMethods.column(BizData::getValue))
                .from(BizData.class).eq(BizData::getType, dataTypeDict.getValueByAlias("nick_name"));
        List<String> nickNameList = dataService.listAs(nickNameQueryWrapper, String.class);
        QueryWrapper photoQueryWrapper = QueryWrapper.create()
                .select(QueryMethods.column(BizData::getValue))
                .from(BizData.class).eq(BizData::getType, dataTypeDict.getValueByAlias("profile_photo"));
        List<String> profilePhotoList = dataService.listAs(photoQueryWrapper, String.class);
        List<BizUser> userList = new ArrayList<>();
        List<CsgoUserInfo> userInfoList = new ArrayList<>();
        if (!nickNameList.isEmpty()) {
            final var secureRandom = new SecureRandom();
            for (int i = 0; i < count; i++) {
                BizUser bizUser = new BizUser();
                bizUser.setId(IdTool.nextId());
                bizUser.setPhoto(profilePhotoList.get(secureRandom.nextInt(profilePhotoList.size())));
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
