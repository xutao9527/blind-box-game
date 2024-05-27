package com.bbg.core.service.impl.csgo;

import cn.hutool.core.util.RandomUtil;
import com.bbg.core.annotation.RedisCache;
import com.bbg.core.annotation.RedisLock;
import com.bbg.core.box.dto.BoxDto;
import com.bbg.core.box.dto.DreamDto;
import com.bbg.core.service.RedisService;
import com.bbg.core.constrans.KeyConst;
import com.bbg.core.service.biz.BizDictService;
import com.bbg.core.service.biz.BizUserService;
import com.bbg.core.service.csgo.*;
import com.bbg.core.utils.FairFactory;
import com.bbg.core.utils.IdTool;
import com.bbg.model.biz.BizDict;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.*;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.core.mapper.csgo.CsgoBoxMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * CSGO箱子 服务层实现。
 *
 * @author bbg
 * @since 2024-05-22
 */
@Service
@RequiredArgsConstructor
public class CsgoBoxServiceImpl extends ServiceImpl<CsgoBoxMapper, CsgoBox> implements CsgoBoxService {
    public final CsgoStorehouseService csgoStorehouseService;
    public final CsgoCapitalRecordService csgoCapitalRecordService;
    public final BizUserService bizUserService;
    public final CsgoUserInfoService csgoUserInfoService;
    public final RedisService redisService;
    public final CsgoBoxGoodsService csgoBoxGoodsService;
    public final BizDictService bizDictService;
    public final CsgoOpenBoxLogService csgoOpenBoxLogService;
    public final CsgoDreamGoodLogService csgoDreamGoodLogService;

    @Lazy
    @Autowired
    private CsgoBoxService selfProxy;

    /**
     * 根据编号获得盲盒
     */
    @RedisCache(value = "#id", key = KeyConst.BOX_ID)
    public CsgoBox getBoxById(Long id) {
        CsgoBox box = getMapper().selectOneWithRelationsByQuery(
                QueryWrapper.create(new CsgoBox()
                        .setId(id)
                        .setEnable(true))
        );
        return this.transformBoxGoods(box);
    }

    /**
     * 根据类型获得盲盒列表
     */
    public List<CsgoBox> getBoxesByType(String type) {
        QueryWrapper queryWrapper = QueryWrapper.create(new CsgoBox().setEnable(true).setType(type));
        List<CsgoBox> csgoBoxes = getMapper().selectListWithRelationsByQuery(queryWrapper);
        csgoBoxes.forEach(this::transformBoxGoods);
        return csgoBoxes;
    }

    /**
     * 开盲盒
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisLock(value = "#bizUser.id", key = KeyConst.METHOD_OPEN_BOX_LOCK)
    public BoxDto.OpenBoxRes openBox(BizUser bizUser, Long boxId) {
        BoxDto.OpenBoxRes boxRes = new BoxDto.OpenBoxRes();
        // 使用FairEntity,进行roll点
        FairFactory.FairEntity fairEntity = FairFactory.build(bizUser);
        int roundNumber = fairEntity.roll(bizUser.getCsgoUserInfo().getRoundNumber());
        // 获得盲盒对象,并抽饰品
        CsgoBox csgoBox = selfProxy.getBoxById(boxId);
        CsgoBoxGoods luckGood = csgoBox.getCsgoBoxGoods().stream()
                .filter(boxGood -> boxGood.getStartRoundNumber().compareTo(BigDecimal.valueOf(roundNumber)) <= 0
                        && boxGood.getEndRoundNumber().compareTo(BigDecimal.valueOf(roundNumber)) >= 0)
                .findFirst().orElse(null);

        if (luckGood != null) {
            // 开箱记录
            CsgoOpenBoxLog csgoOpenBoxLog = openBoxLog(bizUser, csgoBox, luckGood);
            boxRes.setCsgoOpenBoxLog(csgoOpenBoxLog);
            // 派发装备(添加商品到用户背包)
            BizDict goodSourceTypeDict = bizDictService.getDictByTag("csgo_good_source_type");
            dispatchGood(bizUser, luckGood, goodSourceTypeDict.getValueByAlias("source_open_box"));
            // 更新递增后roll点回合数
            CsgoUserInfo updateUserInfo = UpdateEntity.of(CsgoUserInfo.class, bizUser.getCsgoUserInfo().getId());
            UpdateWrapper<CsgoUserInfo> updateWrapper = UpdateWrapper.of(updateUserInfo);
            String updateRoundNumber = "%s %s %s".formatted(QueryMethods.column(CsgoUserInfo::getRoundNumber).getName(), "+", "1");
            updateWrapper.setRaw(CsgoUserInfo::getRoundNumber, updateRoundNumber);
            csgoUserInfoService.updateById(updateUserInfo);
            // 构造流水记录
            CsgoCapitalRecord capitalRecord = new CsgoCapitalRecord();
            capitalRecord.setUserId(bizUser.getId())
                    .setSourceId(boxId.toString())
                    .setType(bizDictService.getDictByTag("csgo_capital_type").getValueByAlias("open_box"))      // 流水类型
                    .setChangeMoney(csgoBox.getPrice().negate());                                               // 扣钱,转为负数
            // 更新用户金额
            bizUser = bizUserService.updateUserMoney(bizUser, capitalRecord);
            boxRes.setMoney(bizUser.getMoney());
        }
        return boxRes;
    }

    /**
     * 进行追梦
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisLock(value = "#bizUser.id", key = KeyConst.METHOD_DREAM_GOOD_LOCK)
    public DreamDto.DreamGoodRes dreamGood(BizUser bizUser, DreamDto.DreamGoodReq model) {
        DreamDto.DreamGoodRes dreamGoodRes = new DreamDto.DreamGoodRes();
        CsgoBoxGoods csgoBoxGoods = csgoBoxGoodsService.getById(model.getBoxGoodId());
        // 计算扣除的用户金额
        BigDecimal consumeMoney =
                csgoBoxGoods.getPrice()
                        .multiply(csgoBoxGoods.getRate())
                        .multiply(model.getProbability())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        // roll点数
        FairFactory.FairEntity fairEntity = FairFactory.build(bizUser);
        int round = RandomUtil.randomInt(1, FairFactory.FairEntity.SEED_MAX_ROLL);
        int roundNumber = fairEntity.roll(round);
        // 中奖区间
        BigDecimal winningRange = model.getProbability()
                .multiply(BigDecimal.valueOf(FairFactory.FairEntity.SEED_MAX_ROLL))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        // 判断是否中奖
        boolean isWinGood = roundNumber < winningRange.intValue();
        if (isWinGood) {
            // 派发装备(添加商品到用户背包)
            BizDict goodSourceTypeDict = bizDictService.getDictByTag("csgo_good_source_type");
            dispatchGood(bizUser, csgoBoxGoods, goodSourceTypeDict.getValueByAlias("source_dream_good"));
        }
        // 追梦记录
        CsgoDreamGoodLog csgoDreamGoodLog = dreamGoodLog(bizUser, csgoBoxGoods, consumeMoney, model.getProbability(), isWinGood);
        dreamGoodRes.setCsgoDreamGoodLog(csgoDreamGoodLog);
        // 构造流水记录
        CsgoCapitalRecord capitalRecord = new CsgoCapitalRecord();
        capitalRecord.setUserId(bizUser.getId())
                .setSourceId(model.getBoxGoodId().toString())
                .setType(bizDictService.getDictByTag("csgo_capital_type").getValueByAlias("dream"))  // 流水类型
                .setChangeMoney(consumeMoney.negate());    // 扣钱,转为负数
        // 更新用户金额
        bizUser = bizUserService.updateUserMoney(bizUser, capitalRecord);
        dreamGoodRes.setMoney(bizUser.getMoney());
        return dreamGoodRes;
    }

    public CsgoOpenBoxLog openBoxLog(BizUser bizUser, CsgoBox box, CsgoBoxGoods boxGood) {
        CsgoOpenBoxLog csgoOpenBoxLog = new CsgoOpenBoxLog();
        csgoOpenBoxLog
                .setId(IdTool.nextId())
                .setOpenBoxTime(LocalDateTime.now())
                .setUserId(bizUser.getId()).setUserNickName(bizUser.getNickName()).setUserPhoto(bizUser.getPhoto())
                .setBoxId(boxGood.getBoxId()).setBoxName(box.getName()).setBoxNameAlias(box.getNameAlias()).setBoxImageUrl(box.getImageUrl()).setBoxPrice(box.getPrice())
                .setGoodId(boxGood.getGoodId()).setGoodName(boxGood.getName()).setGoodNameAlias(boxGood.getNameAlias()).setGoodImageUrl(boxGood.getImageUrl()).setGoodPrice(boxGood.getPrice());
        csgoOpenBoxLogService.save(csgoOpenBoxLog);
        return csgoOpenBoxLog;
    }

    public CsgoDreamGoodLog dreamGoodLog(BizUser bizUser, CsgoBoxGoods boxGood, BigDecimal dreamPrice, BigDecimal dreamGoodProbability, boolean dreamIsWin) {
        CsgoDreamGoodLog csgoDreamGoodLog = new CsgoDreamGoodLog();
        csgoDreamGoodLog
                .setId(IdTool.nextId())
                .setDreamIsWin(dreamIsWin)
                .setDreamPrice(dreamPrice)
                .setDreamGoodProbability(dreamGoodProbability)
                .setDreamGoodTime(LocalDateTime.now())
                .setUserId(bizUser.getId()).setUserNickName(bizUser.getNickName()).setUserPhoto(bizUser.getPhoto())
                .setBoxId(boxGood.getBoxId())
                .setGoodId(boxGood.getGoodId()).setGoodName(boxGood.getName()).setGoodNameAlias(boxGood.getNameAlias()).setGoodImageUrl(boxGood.getImageUrl()).setGoodPrice(boxGood.getPrice());
        csgoDreamGoodLogService.save(csgoDreamGoodLog);
        return csgoDreamGoodLog;
    }

    /**
     * 派发装备(添加商品到用户背包)
     */
    @Transactional(rollbackFor = Exception.class)
    public CsgoStorehouse dispatchGood(BizUser bizUser, CsgoBoxGoods luckGood, String sourceType) {
        BizDict goodStatusDict = bizDictService.getDictByTag("csgo_good_status");
        CsgoStorehouse storehouse = new CsgoStorehouse();
        storehouse
                .setId(IdTool.nextId())
                .setUserId(bizUser.getId())
                .setStatus(goodStatusDict.getValueByAlias("normal"))
                .setSourceId(luckGood.getBoxId())
                .setSourceType(sourceType)
                .setGoodId(luckGood.getGoodId())
                .setName(luckGood.getName())
                .setNameAlias(luckGood.getNameAlias())
                .setImageUrl(luckGood.getImageUrl())
                .setPrice(luckGood.getPrice());
        csgoStorehouseService.save(storehouse);
        return storehouse;
    }

    /**
     * 转化并设置盲盒中商品的roll点范围
     */
    private CsgoBox transformBoxGoods(CsgoBox csgoBox) {
        AtomicReference<BigDecimal> startRound = new AtomicReference<>(BigDecimal.ZERO);
        List<CsgoBoxGoods> boxGoods = csgoBox.getCsgoBoxGoods().stream().peek(boxGood -> {
            BigDecimal roundWeight = boxGood.getRate()
                    .multiply(BigDecimal.valueOf(FairFactory.FairEntity.SEED_MAX_ROLL))
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
            boxGood.setStartRoundNumber(startRound.get().add(BigDecimal.ONE)).setEndRoundNumber(startRound.get().add(roundWeight));
            startRound.updateAndGet(currentValue -> currentValue.add(roundWeight));
        }).toList();
        csgoBox.setCsgoBoxGoods(boxGoods);
        return csgoBox;
    }
}
