package com.bbg.schedule.service.impl.csgo;

import com.bbg.core.annotation.RedisCache;
import com.bbg.core.annotation.RedisClear;
import com.bbg.core.annotation.RedisLock;
import com.bbg.core.constants.KeyConst;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.biz.BizDict;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoRollGood;
import com.bbg.model.csgo.CsgoRollUser;
import com.bbg.model.csgo.CsgoStorehouse;
import com.bbg.schedule.service.biz.BizDictService;
import com.bbg.schedule.service.biz.BizUserService;
import com.bbg.schedule.service.csgo.CsgoRollGoodService;
import com.bbg.schedule.service.csgo.CsgoRollUserService;
import com.bbg.schedule.service.csgo.CsgoStorehouseService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoRoll;
import com.bbg.schedule.mapper.csgo.CsgoRollMapper;
import com.bbg.schedule.service.csgo.CsgoRollService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Roll房间 服务层实现。
 *
 * @author bbg
 * @since 2024-05-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CsgoRollServiceImpl extends ServiceImpl<CsgoRollMapper, CsgoRoll> implements CsgoRollService {
    public final BizDictService bizDictService;
    public final CsgoRollGoodService csgoRollGoodService;
    public final BizUserService bizUserService;
    public final CsgoStorehouseService csgoStorehouseService;
    public final CsgoRollUserService csgoRollUserService;
    @Lazy
    @Autowired
    private CsgoRollService selfProxy;
    // 单独的房间信息-缓存存活时长
    public final static long ROLL_INFO_LIVE_TIME = 180;

    public final static long ROLL_LIST_LIVE_TIME = 500;

    /**
     * 获得撸房信息
     * 缓存信息默认存储180秒(根据内存实时调整)
     */
    @RedisCache(value = "#rollId", key = KeyConst.ROLL_INFO_ID, liveTime = ROLL_INFO_LIVE_TIME, timeUnit = TimeUnit.SECONDS)
    public CsgoRoll getInfo(Long rollId) {
        CsgoRoll csgoRoll = getById(rollId);
        if (csgoRoll == null) {
            return null;
        }
        csgoRoll.setRollGoods(csgoRollGoodService.list(QueryWrapper.create(new CsgoRollGood().setRollId(rollId))));
        return csgoRoll;
    }

    /**
     * 撸房装备派发
     */
    private void dispatchRollGoods(CsgoRoll roll) {
        List<CsgoStorehouse> storehouseList = new ArrayList<>();
        BizDict userTypeDict = bizDictService.getDictByTag("user_type");
        roll.getRollGoods().forEach(rollGood -> {
            if (rollGood.getLuckUserId() != null) {
                BizUser user = bizUserService.getById(rollGood.getLuckUserId());
                if (user.getType().equals(userTypeDict.getValueByAlias("real_user"))
                        || user.getType().equals(userTypeDict.getValueByAlias("test_user"))) {
                    CsgoStorehouse storehouse = new CsgoStorehouse();
                    BizDict goodSourceTypeDict = bizDictService.getDictByTag("csgo_good_source_type");
                    BizDict goodStatusDict = bizDictService.getDictByTag("csgo_good_status");
                    storehouse.setUserId(rollGood.getLuckUserId())
                            .setStatus(goodStatusDict.getValueByAlias("normal"))
                            .setSourceId(roll.getId())
                            .setSourceType(goodSourceTypeDict.getValueByAlias("source_roll_room"))
                            .setName(rollGood.getName())
                            .setNameAlias(rollGood.getNameAlias())
                            .setGoodId(rollGood.getGoodId())
                            .setImageUrl(rollGood.getGoodImage())
                            .setPrice(rollGood.getGoodPrice());
                    storehouseList.add(storehouse);
                }
            }
        });
        csgoStorehouseService.saveBatch(storehouseList);
    }

    /**
     * 撸房具体逻辑
     */
    private boolean runRoll(CsgoRoll roll) {
        SecureRandom random = new SecureRandom();
        BizDict rollStatusDict = bizDictService.getDictByTag("csgo_roll_status");
        List<CsgoRollUser> rollUsers = csgoRollUserService.list(QueryWrapper.create(new CsgoRollUser().setRollId(roll.getId())));
        List<CsgoRollGood> rollGoods = new ArrayList<>(roll.getRollGoods());
        long randomCount = Math.min(rollUsers.size(), roll.getRollGoods().size());                  // 抽奖次数
        for (int i = 0; i < randomCount; i++) {
            int luckUserIndex = random.nextInt(rollUsers.size());
            int luckGoodIndex = random.nextInt(roll.getRollGoods().size());
            var luckUser = rollUsers.get(luckUserIndex);
            var luckGood = rollGoods.get(luckGoodIndex);
            luckGood.setLuckUserId(luckUser.getUserId());                                           // 设置抽中商品的用户
            rollUsers.remove(luckUserIndex);
            rollGoods.remove(luckGoodIndex);
        }
        roll.setStatus(rollStatusDict.getValueByAlias("roll_offline"));                             // 房间状态(对战结束)
        return true;
    }

    /**
     * 上线房间
     */
    @RedisLock(value = "#csgoRoll.id", key = KeyConst.METHOD_JOIN_ROLL_LOCK)
    @RedisClear(value = "#csgoRoll.id", key = KeyConst.ROLL_INFO_ID)
    public boolean onlineRoll(CsgoRoll csgoRoll) {
        var rollStatusDict = bizDictService.getDictByTag("csgo_roll_status");
        csgoRoll.setStatus(rollStatusDict.getValueByAlias("roll_online"));
        updateById(csgoRoll);
        return true;
    }

    /**
     * 下线房间
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisLock(value = "#csgoRoll.id", key = KeyConst.METHOD_JOIN_ROLL_LOCK)
    @RedisClear(value = "#csgoRoll.id", key = KeyConst.ROLL_INFO_ID)
    public boolean offlineRoll(CsgoRoll csgoRoll) {
        var rollStatusDict = bizDictService.getDictByTag("csgo_roll_status");
        CsgoRoll roll = selfProxy.getInfo(csgoRoll.getId());
        if (!rollStatusDict.getValueByAlias("roll_online").equals(roll.getStatus())) {
            log.error("房间没上架:{}", roll.getId());
            return false;
        }
        if (!this.runRoll(roll)) {
            log.error("撸房计算异常:{}", roll.getId());
            return false;
        }
        if (rollStatusDict.getValueByAlias("roll_offline").equals(roll.getStatus())) {
            dispatchRollGoods(roll);                                                                // 发放商品
            csgoRollGoodService.saveOrUpdateBatch(roll.getRollGoods());                             // 更新商品中奖信息
            this.saveOrUpdate(roll);                                                                // 更新房间状态
        }
        return true;
    }
}
