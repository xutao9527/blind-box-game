package com.bbg.box.service.impl.csgo;

import com.bbg.box.service.biz.BizDictService;
import com.bbg.box.service.csgo.CsgoRollGoodService;
import com.bbg.box.service.csgo.CsgoRollUserService;
import com.bbg.core.annotation.RedisCache;
import com.bbg.core.annotation.RedisLock;
import com.bbg.core.box.dto.RollDto;
import com.bbg.core.constants.KeyConst;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.biz.BizDict;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoBattleRoom;
import com.bbg.model.csgo.CsgoRollGood;
import com.bbg.model.csgo.CsgoRollUser;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoRoll;
import com.bbg.box.mapper.csgo.CsgoRollMapper;
import com.bbg.box.service.csgo.CsgoRollService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Roll房间 服务层实现。
 *
 * @author bbg
 * @since 2024-05-16
 */
@Slf4j
@Service
public class CsgoRollServiceImpl extends ServiceImpl<CsgoRollMapper, CsgoRoll> implements CsgoRollService {
    @Autowired
    BizDictService bizDictService;
    @Autowired
    CsgoRollGoodService csgoRollGoodService;
    @Autowired
    CsgoRollUserService csgoRollUserService;
    // 单独的房间信息-缓存存活时长
    public final static long ROLL_INFO_LIVE_TIME = 180;

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
        csgoRoll.setRollUsers(csgoRollUserService.list(QueryWrapper.create(new CsgoRollUser().setRollId(rollId))));
        return csgoRoll;
    }

    /**
     * 加入撸房
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisLock(value = "#rollId", key = KeyConst.METHOD_JOIN_ROLL_LOCK)
    public ApiRet<CsgoRoll> joinRoll(BizUser bizUser, Long rollId) {
        CsgoRoll roll = getInfo(rollId);
        // --------------------------------------检查s--------------------------------------
        if (roll == null || !roll.getEnable()) {
            return ApiRet.buildNo("房间不存在");
        }
        BizDict rollStatusDict = bizDictService.getDictByTag("csgo_roll_status");
        BizDict rollModelDict = bizDictService.getDictByTag("csgo_roll_model");
        if (!rollStatusDict.getValueByAlias("roll_online").equals(roll.getStatus())) {
            return ApiRet.buildNo("房间没上架");
        }
        LocalDateTime currentTime = LocalDateTime.now();
        if(roll.getRollModel().equals(rollModelDict.getValueByAlias("people_number_model"))){
            if(roll.getRollUsers().size()>=roll.getPeopleNumber()){
                ApiRet.buildNo("房间已满员");
            }
        }else if(roll.getRollModel().equals(rollModelDict.getValueByAlias("end_time_model"))){
            if(roll.getEndTime().isBefore(currentTime)){
                ApiRet.buildNo("房间已结束");
            }
        }
        if (roll.getRollUsers().stream().anyMatch(rollUser -> rollUser.getUserId().equals(bizUser.getId()))) {
            return ApiRet.buildNo("用户已加入房间");
        }
        // --------------------------------------检查e--------------------------------------
        // --------------------------------------设置数据s--------------------------------------

        // --------------------------------------设置数据e--------------------------------------
        // --------------------------------------保存数据s--------------------------------------

        // --------------------------------------保存数据e--------------------------------------
        return null;
    }

    /**
     * 获得撸房信息
     * 缓存信息默认存储500毫秒,避免高并发,缓解数据库压力
     */
    @RedisCache(key = KeyConst.ROLL_LIST_INFO, liveTime = 500, timeUnit = TimeUnit.MILLISECONDS)
    public Page<CsgoRoll> getRollList(RollDto.GetRollListReq getRollListReq) {
        BizDict rollStatusDict = bizDictService.getDictByTag("csgo_roll_status");
        rollStatusDict.getValueByAlias("roll_online");
        rollStatusDict.getValueByAlias("roll_offline");
        QueryWrapper queryWrapper = QueryWrapper.create()
                .in(CsgoRoll::getStatus, rollStatusDict.getValueByAlias("roll_online"), rollStatusDict.getValueByAlias("roll_offline"))
                .eq(CsgoRoll::getEnable, true);
        Page<CsgoRoll> rollPage = page(Page.of(getRollListReq.getPageNumber(), getRollListReq.getPageSize()), queryWrapper);
        // 如果redis中有撸房缓存: redis撸房替换结果集,否则数据库查询
        List<CsgoRoll> rollList = rollPage.getRecords().stream().map(
                roll -> {
                    roll = getInfo(roll.getId());
                    return roll;
                }
        ).toList();
        rollPage.setRecords(rollList);
        return rollPage;
    }
}
