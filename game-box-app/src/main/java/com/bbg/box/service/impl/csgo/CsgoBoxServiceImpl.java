package com.bbg.box.service.impl.csgo;

import com.bbg.box.service.biz.BizUserService;
import com.bbg.box.service.csgo.CsgoCapitalRecordService;
import com.bbg.box.service.csgo.CsgoStorehouseService;
import com.bbg.box.service.csgo.CsgoUserInfoService;
import com.bbg.core.box.dto.BoxDto;
import com.bbg.core.service.RedisService;
import com.bbg.core.utils.FairFactory;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.*;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.box.mapper.csgo.CsgoBoxMapper;
import com.bbg.box.service.csgo.CsgoBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * CSGO箱子 服务层实现。
 *
 * @author bbg
 * @since 2024-05-03
 */
@Service
public class CsgoBoxServiceImpl extends ServiceImpl<CsgoBoxMapper, CsgoBox> implements CsgoBoxService {

    @Autowired
    CsgoStorehouseService csgoStorehouseService;
    @Autowired
    CsgoCapitalRecordService csgoCapitalRecordService;
    @Autowired
    BizUserService bizUserService;
    @Autowired
    CsgoUserInfoService csgoUserInfoService;
    @Autowired
    RedisService redisService;


    public CsgoBox getBoxesById(Long id) {
        CsgoBox box = getMapper().selectOneWithRelationsByQuery(
                QueryWrapper.create(new CsgoBox()
                        .setId(id)
                        .setEnable(true))
        );
        return this.transformBoxGoods(box);
    }

    public List<CsgoBox> getBoxesByType(String type) {
        QueryWrapper queryWrapper = QueryWrapper.create(new CsgoBox().setEnable(true).setType(type));
        List<CsgoBox> csgoBoxes = getMapper().selectListWithRelationsByQuery(queryWrapper);
        csgoBoxes.forEach(this::transformBoxGoods);
        return csgoBoxes;
    }

    @Transactional(rollbackFor = Exception.class)
    public BoxDto.OpenBoxRes openBox(BizUser bizUser, Long boxId) {
        BoxDto.OpenBoxRes boxRes = new BoxDto.OpenBoxRes();
        // 使用FairEntity,进行roll点
        FairFactory.FairEntity fairEntity = FairFactory.build(bizUser);
        int roundNumber = fairEntity.roll(bizUser.getCsgoUserInfo().getRoundNumber());
        // 获得盲盒对象,并抽饰品
        CsgoBox csgoBox = this.getBoxesById(boxId);
        CsgoBoxGoods luckGood = csgoBox.getCsgoBoxGoods().stream()
                .filter(boxGood -> boxGood.getStartRoundNumber().compareTo(BigDecimal.valueOf(roundNumber)) <= 0
                        && boxGood.getEndRoundNumber().compareTo(BigDecimal.valueOf(roundNumber)) >= 0)
                .findFirst().orElse(null);

        if (luckGood != null) {
            // 添加商品到用户背包
            CsgoStorehouse storehouse = new CsgoStorehouse();
            storehouse.setUserId(bizUser.getId())
                    .setGoodId(luckGood.getGoodId())
                    .setName(luckGood.getName())
                    .setNameAlias(luckGood.getNameAlias())
                    .setImageUrl(luckGood.getImageUrl())
                    .setPrice(luckGood.getPrice());
            csgoStorehouseService.save(storehouse);
            boxRes.setLuckStorehouse(storehouse);
            // 更新递增后roll点回合数
            CsgoUserInfo csgoUserInfo = new CsgoUserInfo();
            csgoUserInfo.setId(bizUser.getCsgoUserInfo().getId())
                    .setRoundNumber(bizUser.getCsgoUserInfo().getRoundNumber() + 1)
                    .setCreateTime(null)
                    .setUpdateTime(null);
            csgoUserInfoService.updateById(csgoUserInfo);

            System.out.println("扣钱之前:" + bizUser.getMoney());
            System.out.println("回合数之前:" + bizUser.getCsgoUserInfo().getRoundNumber());

            // 构造流水记录
            CsgoCapitalRecord capitalRecord = new CsgoCapitalRecord();
            capitalRecord.setUserId(bizUser.getId())
                    .setChangeMoney(csgoBox.getPrice().negate());    // 扣钱,转为负数
            // 更新用户金额
            bizUser = bizUserService.updateUserMoney(bizUser, capitalRecord);
            System.out.println("盲盒价格:" + csgoBox.getPrice() + "盲盒价格:" + csgoBox.getPrice());
            System.out.println("扣钱之后:" + bizUser.getMoney());
            System.out.println("回合数之后:" + bizUser.getCsgoUserInfo().getRoundNumber());
            System.out.println();
            // 跟新缓存
            redisService.updateUser(bizUser);
            boxRes.setBizUser(bizUser);
        }
        return boxRes;
    }

    /**
     * 转化并设置盲盒中商品的roll点范围
     */
    private CsgoBox transformBoxGoods(CsgoBox csgoBox) {
        AtomicReference<BigDecimal> startRound = new AtomicReference<>(BigDecimal.ZERO);
        List<CsgoBoxGoods> boxGoods = csgoBox.getCsgoBoxGoods().stream().map(boxGood -> {
            BigDecimal roundWeight = boxGood.getRate()
                    .multiply(BigDecimal.valueOf(FairFactory.FairEntity.SEED_MAX_ROLL))
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
            boxGood.setStartRoundNumber(startRound.get().add(BigDecimal.ONE)).setEndRoundNumber(startRound.get().add(roundWeight));
            startRound.updateAndGet(currentValue -> currentValue.add(roundWeight));
            return boxGood;
        }).toList();
        csgoBox.setCsgoBoxGoods(boxGoods);
        return csgoBox;
    }
}
