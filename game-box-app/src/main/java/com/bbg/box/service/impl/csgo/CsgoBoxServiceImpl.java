package com.bbg.box.service.impl.csgo;

import com.bbg.core.utils.FairFactory;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoBoxGoods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoBox;
import com.bbg.box.mapper.csgo.CsgoBoxMapper;
import com.bbg.box.service.csgo.CsgoBoxService;
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

    public CsgoBox getBoxesById(Long id){
        return getMapper().selectOneWithRelationsByQuery(
                QueryWrapper.create(new CsgoBox()
                        .setId(id)
                        .setEnable(true))
        );
    }

    public List<CsgoBox> getBoxesByType(String type){
        QueryWrapper queryWrapper = QueryWrapper.create(new CsgoBox().setEnable(true).setType(type));
        return getMapper().selectListWithRelationsByQuery(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public void openBox(BizUser bizUser, Long boxId){
        FairFactory.FairEntity fairEntity = FairFactory.FairEntity.builder()
                .clientSeed(bizUser.getCsgoUserInfo().getClientSeed())
                .publicHash(bizUser.getCsgoUserInfo().getPublicHash())
                .secretSalt(bizUser.getCsgoUserInfo().getSecretSalt())
                .secretHash(bizUser.getCsgoUserInfo().getSecretHash()).build();
        int RoundNumber = fairEntity.roll(bizUser.getCsgoUserInfo().getRoundNumber());

        CsgoBox csgoBox = this.getBoxesById(boxId);


        AtomicReference<BigDecimal> startRound = new AtomicReference<>(BigDecimal.ZERO);
        List<CsgoBoxGoods> boxGoods = csgoBox.getCsgoBoxGoods().stream().map(boxGood -> {
            BigDecimal roundWeight = boxGood.getRate()
                    .multiply(BigDecimal.valueOf(FairFactory.FairEntity.SEED_MAX_ROLL))
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
            boxGood.setStartRoundNumber(startRound.get().add(BigDecimal.ONE)).setEndRoundNumber(startRound.get().add(roundWeight));
            startRound.updateAndGet(currentValue -> currentValue.add(roundWeight));
            return boxGood;
        }).toList();
        System.out.println(boxGoods);
    }
}
