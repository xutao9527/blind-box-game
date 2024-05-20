package com.bbg.box.service.impl.csgo;

import cn.hutool.core.lang.Pair;
import com.bbg.box.service.biz.BizDictService;
import com.bbg.box.service.biz.BizUserService;
import com.bbg.box.service.csgo.*;
import com.bbg.box.utils.IdTool;
import com.bbg.core.annotation.RedisCache;
import com.bbg.core.annotation.RedisLock;
import com.bbg.core.box.dto.BattleRoomDto;
import com.bbg.core.box.service.RedisService;
import com.bbg.core.constants.KeyConst;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.utils.FairFactory;
import com.bbg.model.biz.BizDict;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.*;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.box.mapper.csgo.CsgoBattleRoomMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 对战房间 服务层实现。
 *
 * @author bbg
 * @since 2024-05-13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CsgoBattleRoomServiceImpl extends ServiceImpl<CsgoBattleRoomMapper, CsgoBattleRoom> implements CsgoBattleRoomService {

    public final BizDictService bizDictService;
    public final CsgoBoxService csgoBoxService;
    public final CsgoRobotService csgoRobotService;
    public final CsgoBattleRoomUserService csgoBattleRoomUserService;
    public final CsgoBattleRoomBoxService csgoBattleRoomBoxService;
    public final CsgoBattleRoomGoodService csgoBattleRoomGoodService;
    public final BizUserService bizUserService;
    public final CsgoStorehouseService csgoStorehouseService;
    public final RedisService redisService;

    @Lazy
    @Autowired
    private CsgoBattleRoomService selfProxy;

    // 单独的房间信息-缓存存活时长
    public final static long ROOM_INFO_LIVE_TIME = 180;

    /**
     * 创建对战房间
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisLock(value = "#bizUser.id", key = KeyConst.METHOD_CREATE_ROOM_LOCK)
    public ApiRet<BattleRoomDto.BattleRoomRes> createRoom(BizUser bizUser, BattleRoomDto.CreateRoomReq createRoomReq, long roomId) {
        BattleRoomDto.BattleRoomRes battleRoomRes = new BattleRoomDto.BattleRoomRes();      // 返回结果
        List<CsgoRobot> robotList = null;                                                   // 机器人
        BigDecimal roomPrice;                                                               // 房间价格
        // --------------------------------------检查s--------------------------------------
        // 箱子检查
        BizDict boxTypeDict = bizDictService.getDictByTag("csgo_box_type");
        Map<Long, CsgoBox> allBoxMap = csgoBoxService.getBoxesByType(boxTypeDict.getValueByAlias("battle_box"))
                .stream().collect(Collectors.toMap(CsgoBox::getId, csgoBox -> csgoBox));
        List<CsgoBox> boxList = Arrays.stream(createRoomReq.getBoxesId())
                .mapToObj(allBoxMap::get)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(CsgoBox::getId))
                .toList();
        if (boxList.isEmpty()) {
            return ApiRet.buildNo("缺少箱子");
        }
        // 余额检查
        roomPrice = boxList.stream().map(CsgoBox::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (bizUser.getMoney().compareTo(roomPrice) < 0) {
            return ApiRet.buildNo("金额不够");
        }
        // 机器人设置
        if (createRoomReq.getBoxesId().length > 0) {
            Map<Long, CsgoRobot> allRobotMap = csgoRobotService.list()
                    .stream().collect(Collectors.toMap(CsgoRobot::getId, csgoRobot -> csgoRobot));
            robotList = Arrays.stream(createRoomReq.getRobotsId())
                    .mapToObj(allRobotMap::get)
                    .filter(Objects::nonNull)
                    .limit(createRoomReq.getPeopleNumber() - 1)
                    .toList();
        }
        // --------------------------------------检查e--------------------------------------
        // --------------------------------------设置数据s--------------------------------------
        BizDict battleStatusDict = bizDictService.getDictByTag("csgo_battle_status");
        FairFactory.FairEntity fairEntity = FairFactory.build();
        // 房间
        CsgoBattleRoom battleRoom = new CsgoBattleRoom();
        battleRoom.setId(roomId)
                .setCreateUserId(bizUser.getId())
                .setBattleModel(createRoomReq.getBattleModel())
                .setPeopleNumber(createRoomReq.getPeopleNumber())
                .setRoomPrice(roomPrice)
                .setStatus(battleStatusDict.getValueByAlias("battle_wait"))                 // 房间状态(等待玩家)
                .setSecretHash(fairEntity.getSecretHash())
                .setSecretSalt(fairEntity.getSecretSalt())
                .setPublicHash(fairEntity.getPublicHash())
                .setClientSeed(fairEntity.getClientSeed());
        // 房间关联的箱子
        boxList.forEach(box -> {
            CsgoBattleRoomBox roomBox = new CsgoBattleRoomBox();
            roomBox.setId(IdTool.nextId()).setBoxId(box.getId()).setRoomId(roomId).setName(box.getName()).setNameAlias(box.getNameAlias()).setImageUrl(box.getImageUrl());
            battleRoom.getRoomBoxes().add(roomBox);                                         // 添加参战箱子
        });
        // 房间关联的用户
        CsgoBattleRoomUser createUser = new CsgoBattleRoomUser();
        createUser.setId(IdTool.nextId()).setRoomId(roomId).setUserId(bizUser.getId()).setUserType(bizUser.getType()).setNickName(bizUser.getNickName()).setImageUrl(bizUser.getPhoto());
        battleRoom.getRoomUsers().add(createUser);                                          // 添加参战用户(创建人)
        if (null != robotList && !robotList.isEmpty()) {
            BizDict userTypeDict = bizDictService.getDictByTag("user_type");
            robotList.forEach(robot -> {
                CsgoBattleRoomUser roomUser = new CsgoBattleRoomUser();
                roomUser.setId(IdTool.nextId()).setRoomId(roomId).setUserId(robot.getId()).setUserType(userTypeDict.getValueByAlias("robot")).setNickName(robot.getName()).setImageUrl(robot.getImageUrl());
                battleRoom.getRoomUsers().add(roomUser);                                    // 添加参战用户(机器人)
            });
        }
        // 人满,计算对战奖励
        if (battleRoom.getPeopleNumber() == battleRoom.getRoomUsers().size()) {
            if (!this.runBattle(battleRoom)) {
                return ApiRet.buildNo("对战计算异常");
            }
        }
        // --------------------------------------设置数据e--------------------------------------
        // --------------------------------------保存数据s--------------------------------------
        // 构造流水记录
        CsgoCapitalRecord userCapitalRecord = new CsgoCapitalRecord();
        userCapitalRecord.setUserId(bizUser.getId())
                .setSourceId(battleRoom.getId().toString())
                .setType(bizDictService.getDictByTag("csgo_capital_type").getValueByAlias("battle"))    // 流水类型
                .setChangeMoney(battleRoom.getRoomPrice().negate());                                    // 扣钱,转为负数
        // 更新用户金额
        bizUser = bizUserService.updateUserMoney(bizUser, userCapitalRecord);
        battleRoomRes.setBizUser(bizUser);
        // 判断房间状态等于[对战结束],进行 [对战结果保存] 和 [装备派发]
        if (!battleRoom.getRoomGoods().isEmpty() && battleStatusDict.getValueByAlias("battle_end").equals(battleRoom.getStatus())) {
            csgoBattleRoomGoodService.saveOrUpdateBatch(battleRoom.getRoomGoods());
            dispatchBattleGoods(battleRoom);
        }
        this.save(battleRoom);
        csgoBattleRoomBoxService.saveOrUpdateBatch(battleRoom.getRoomBoxes());
        csgoBattleRoomUserService.saveOrUpdateBatch(battleRoom.getRoomUsers());
        // --------------------------------------保存数据e--------------------------------------
        battleRoomRes.setCsgoBattleRoom(battleRoom);
        // 更新 [房间缓存]
        redisService.set(KeyConst.build(KeyConst.ROOM_INFO_ID, battleRoom.getId().toString()), battleRoom, ROOM_INFO_LIVE_TIME, TimeUnit.SECONDS);
        return ApiRet.buildOk(battleRoomRes);
    }

    /**
     * 加入对战房间
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisLock(value = "#roomId", key = KeyConst.METHOD_JOIN_ROOM_LOCK)
    public ApiRet<BattleRoomDto.BattleRoomRes> joinRoom(BizUser bizUser, Long roomId) {
        BattleRoomDto.BattleRoomRes battleRoomRes = new BattleRoomDto.BattleRoomRes();
        CsgoBattleRoom battleRoom = selfProxy.getInfo(roomId);
        // --------------------------------------检查s--------------------------------------
        if (battleRoom == null) {
            return ApiRet.buildNo("房间不存在");
        }
        // 状态检查
        BizDict battleStatusDict = bizDictService.getDictByTag("csgo_battle_status");
        if (!battleRoom.getStatus().equals(battleStatusDict.getValueByAlias("battle_wait"))) {
            return ApiRet.buildNo("房间已结束");
        }
        // 余额检查
        if (bizUser.getMoney().compareTo(battleRoom.getRoomPrice()) < 0) {
            return ApiRet.buildNo("金额不够");
        }
        // 已加入检查检查
        Long userId = bizUser.getId();
        if (battleRoom.getRoomUsers().stream().anyMatch(roomUser -> roomUser.getUserId().equals(userId))) {
            return ApiRet.buildNo("用户已加入房间");
        }
        // --------------------------------------检查s--------------------------------------
        // --------------------------------------设置数据s--------------------------------------
        // 添加新加入房间的用户
        CsgoBattleRoomUser roomUser = new CsgoBattleRoomUser();
        roomUser.setId(IdTool.nextId()).setRoomId(roomId).setUserId(bizUser.getId()).setUserType(bizUser.getType()).setNickName(bizUser.getNickName()).setImageUrl(bizUser.getPhoto());
        battleRoom.getRoomUsers().add(roomUser);
        // 判断房间状态等于 [等待中] 和 [人数已满] ,计算对战奖励
        if (battleRoom.getPeopleNumber() == battleRoom.getRoomUsers().size() && battleRoom.getStatus().equals(battleStatusDict.getValueByAlias("battle_wait"))) {
            if (!this.runBattle(battleRoom)) {
                return ApiRet.buildNo("对战计算异常");
            }
        }
        // --------------------------------------设置数据e--------------------------------------
        // --------------------------------------保存数据s--------------------------------------
        // 构造流水记录
        CsgoCapitalRecord capitalRecord = new CsgoCapitalRecord();
        capitalRecord.setUserId(bizUser.getId())
                .setSourceId(battleRoom.getId().toString())
                .setType(bizDictService.getDictByTag("csgo_capital_type").getValueByAlias("battle"))    // 流水类型
                .setChangeMoney(battleRoom.getRoomPrice().negate());                                    // 扣钱,转为负数
        // 更新用户金额
        bizUser = bizUserService.updateUserMoney(bizUser, capitalRecord);
        battleRoomRes.setBizUser(bizUser);
        // 判断房间状态等于[对战结束],进行 [对战结果保存] 和 [装备派发]
        if (!battleRoom.getRoomGoods().isEmpty() && battleStatusDict.getValueByAlias("battle_end").equals(battleRoom.getStatus())) {
            csgoBattleRoomGoodService.saveOrUpdateBatch(battleRoom.getRoomGoods());                     // 保存房间中奖商品
            csgoBattleRoomUserService.saveOrUpdateBatch(battleRoom.getRoomUsers());                     // 更新用户
            dispatchBattleGoods(battleRoom);                                                            // 发放中奖商品
            this.saveOrUpdate(battleRoom);                                                              // 更新房间状态
        }
        csgoBattleRoomUserService.save(roomUser);                                                       // 添加新加入用户
        // --------------------------------------保存数据e--------------------------------------
        battleRoomRes.setCsgoBattleRoom(battleRoom);
        // 更新 [房间缓存]
        redisService.set(KeyConst.build(KeyConst.ROOM_INFO_ID, battleRoom.getId().toString()), battleRoom, ROOM_INFO_LIVE_TIME, TimeUnit.SECONDS);
        return ApiRet.buildOk(battleRoomRes);
    }

    /**
     * 获得对战房间信息
     * 缓存信息默认存储180秒(根据内存实时调整)
     */
    @RedisCache(value = "#roomId", key = KeyConst.ROOM_INFO_ID, liveTime = ROOM_INFO_LIVE_TIME, timeUnit = TimeUnit.SECONDS)
    public CsgoBattleRoom getInfo(Long roomId) {
        CsgoBattleRoom battleRoom = getById(roomId);
        if (battleRoom == null) {
            return null;
        }
        battleRoom.setRoomBoxes(csgoBattleRoomBoxService.list(QueryWrapper.create(new CsgoBattleRoomBox().setRoomId(roomId))));
        battleRoom.setRoomUsers(csgoBattleRoomUserService.list(QueryWrapper.create(new CsgoBattleRoomUser().setRoomId(roomId))));
        BizDict battleStatusDict = bizDictService.getDictByTag("csgo_battle_status");
        if (battleRoom.getStatus().equals(battleStatusDict.getValueByAlias("battle_end"))) {
            battleRoom.setRoomGoods(csgoBattleRoomGoodService.list(QueryWrapper.create(new CsgoBattleRoomGood().setRoomId(roomId))));
        }
        return battleRoom;
    }

    /**
     * 对战结果派发装备
     */
    @Transactional(rollbackFor = Exception.class)
    public void dispatchBattleGoods(CsgoBattleRoom csgoBattleRoom) {
        List<CsgoStorehouse> storehouseList = new ArrayList<>();
        BizDict userTypeDict = bizDictService.getDictByTag("user_type");
        csgoBattleRoom.getRoomGoods().forEach(roomGood -> {
            csgoBattleRoom.getRoomUsers().stream()
                    .filter(roomUser -> roomGood.getLuckUserId() != null && roomGood.getLuckUserId().equals(roomUser.getUserId()))
                    .findFirst().ifPresent(
                            user -> {
                                // 当用户等于真实用户和测试用户的时候,才进行装备派发
                                if (user.getUserType().equals(userTypeDict.getValueByAlias("real_user"))
                                        || user.getUserType().equals(userTypeDict.getValueByAlias("test_user"))) {
                                    CsgoStorehouse storehouse = new CsgoStorehouse();
                                    storehouse.setUserId(roomGood.getLuckUserId())
                                            .setName(roomGood.getName())
                                            .setNameAlias(roomGood.getNameAlias())
                                            .setGoodId(roomGood.getGoodId())
                                            .setImageUrl(roomGood.getGoodImage())
                                            .setPrice(roomGood.getGoodPrice());
                                    storehouseList.add(storehouse);
                                }
                            }
                    );
        });
        csgoStorehouseService.saveBatch(storehouseList);
    }

    /**
     * 对战的具体逻辑
     */
    public boolean runBattle(CsgoBattleRoom csgoBattleRoom) {
        AtomicInteger round = new AtomicInteger(0);
        FairFactory.FairEntity fairEntity = FairFactory.build(csgoBattleRoom);
        List<CsgoBattleRoomGood> roomGoods = new ArrayList<>();
        csgoBattleRoom.getRoomBoxes().forEach(box -> {
            csgoBattleRoom.getRoomUsers().forEach(user -> {
                CsgoBox csgoBox = csgoBoxService.getBoxById(box.getBoxId());
                int roundNumber = fairEntity.roll(round.getAndIncrement());
                CsgoBoxGoods luckGood = csgoBox.getCsgoBoxGoods().stream()
                        .filter(boxGood -> boxGood.getStartRoundNumber().compareTo(BigDecimal.valueOf(roundNumber)) <= 0
                                && boxGood.getEndRoundNumber().compareTo(BigDecimal.valueOf(roundNumber)) >= 0)
                        .findFirst().orElse(null);
                if (luckGood != null) {
                    CsgoBattleRoomGood roomGood = new CsgoBattleRoomGood();
                    roomGood.setRoomId(csgoBattleRoom.getId())
                            .setRollUserId(user.getUserId())
                            .setRound(round.get())
                            .setRoundNumber(roundNumber)
                            .setBoxId(box.getBoxId())
                            .setGoodId(luckGood.getGoodId())
                            .setGoodPrice(luckGood.getPrice())
                            .setGoodImage(luckGood.getImageUrl()).setName(luckGood.getName())
                            .setNameAlias(luckGood.getNameAlias());
                    roomGoods.add(roomGood);
                    System.out.printf("user:%s round:%s roundNumber:%s boxId:%s good:%s%n",
                            user.getUserId(), round.get(), roundNumber, box.getBoxId(), luckGood.getGoodId());
                }
            });
        });
        // 中奖结果根据 uid 分组
        Map<Long, List<CsgoBattleRoomGood>> roomGoodMap = roomGoods.stream().collect(Collectors.groupingBy(CsgoBattleRoomGood::getRollUserId));
        // 统计每个用户的中奖总额
        List<Pair<Long, BigDecimal>> userSumMoneyList = roomGoodMap.keySet().stream().map(uid -> {                  // 所有用户列表
            BigDecimal sumGoodPrice = roomGoodMap.get(uid).stream().map(CsgoBattleRoomGood::getGoodPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            return new Pair<>(uid, sumGoodPrice);
        }).toList();
        Pair<Long, BigDecimal> winUser;                                                                             // 赢的用户
        List<Pair<Long, BigDecimal>> winUserList;                                                                   // 赢的用户列表
        BizDict battleModelDict = bizDictService.getDictByTag("csgo_battle_model");
        if (battleModelDict.getValueByAlias("max_price_model").equals(csgoBattleRoom.getBattleModel())) {           // 欧皇模式赢家
            winUser = userSumMoneyList.stream().max(Comparator.comparing(Pair::getValue)).orElse(null);
        } else if (battleModelDict.getValueByAlias("min_price_model").equals(csgoBattleRoom.getBattleModel())) {    // 非酋模式赢家
            winUser = userSumMoneyList.stream().min(Comparator.comparing(Pair::getValue)).orElse(null);
        } else {
            return false;
        }
        winUserList = userSumMoneyList.stream().filter(
                u -> u.getValue().compareTo(winUser.getValue()) == 0 && u.getKey().longValue() != winUser.getKey().longValue()
        ).collect(Collectors.toList());
        winUserList.add(winUser);
        if (winUserList.size() == 1) {                                                                  // 玩家独赢
            roomGoods.forEach(roomGood -> roomGood.setLuckUserId(winUserList.get(0).getKey()));         // 变更所有装备给赢家
            csgoBattleRoom.getRoomUsers().forEach(u -> {
                u.setIsWin(u.getUserId().equals(winUserList.get(0).getKey()));                          // 设置赢家(一个)
            });
        } else if (winUserList.size() == csgoBattleRoom.getPeopleNumber()) {                            // 玩家全赢
            roomGoods.forEach(roomGood -> roomGood.setLuckUserId(roomGood.getRollUserId()));            // 变更所有装备给赢家(自己拿自己的)
            csgoBattleRoom.getRoomUsers().forEach(roomUser -> roomUser.setIsWin(true));                 // 设置赢家(所有)
        } else {                                                                                        // 超过一个赢家
            roomGoods.forEach(roomGood -> {
                if (winUserList.stream().anyMatch(u -> u.getKey().equals(roomGood.getRollUserId()))) {
                    roomGood.setLuckUserId(roomGood.getRollUserId());                                   // 变更所有装备给赢家(自己拿自己的)
                    csgoBattleRoom.getRoomUsers().forEach(u -> {
                        if (roomGood.getRollUserId().equals(u.getUserId())) {
                            u.setIsWin(true);                                                           // 设置赢家(部分)
                        }
                    });
                }
            });
            // 输的用户列表
            List<Pair<Long, BigDecimal>> loseUserList = userSumMoneyList.stream().filter(p -> winUserList.stream().noneMatch(w -> w.getKey().equals(p.getKey()))).toList();
            // 输的装备总值
            BigDecimal loseSumMoney = loseUserList.stream().map(Pair::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
            // 输的装备总值,构建福利饰品赠送给赢家
            BizDict boxTypeDict = bizDictService.getDictByTag("csgo_box_type");
            CsgoBox luckyBagBox = csgoBoxService.getBoxesByType(boxTypeDict.getValueByAlias("lucky_bag")).stream().findFirst().orElse(null);
            if (luckyBagBox != null && !luckyBagBox.getCsgoBoxGoods().isEmpty()) {
                winUserList.forEach(p -> {
                    CsgoBattleRoomGood roomGood = new CsgoBattleRoomGood();
                    roomGood.setRoomId(csgoBattleRoom.getId())
                            .setLuckUserId(p.getKey())                                                  // 分福利饰品给赢家
                            .setGoodId(luckyBagBox.getCsgoBoxGoods().get(0).getGoodId())
                            .setGoodPrice(loseSumMoney.divide(BigDecimal.valueOf(winUserList.size()), RoundingMode.HALF_UP))
                            .setGoodImage(luckyBagBox.getCsgoBoxGoods().get(0).getImageUrl())
                            .setName(luckyBagBox.getCsgoBoxGoods().get(0).getName())
                            .setNameAlias(luckyBagBox.getCsgoBoxGoods().get(0).getNameAlias());
                    roomGoods.add(roomGood);
                });
            }
        }
        csgoBattleRoom.setRoomGoods(roomGoods);
        BizDict battleStatusDict = bizDictService.getDictByTag("csgo_battle_status");
        csgoBattleRoom.setStatus(battleStatusDict.getValueByAlias("battle_end"));                       // 房间状态(对战结束)
        return true;
    }

    /**
     * 获得对战房间信息
     * 缓存信息默认存储500毫秒,避免高并发,缓解数据库压力
     */
    @RedisCache(value = "#getRoomListReq.battleModel", key = KeyConst.ROOM_LIST_INFO_BATTLE_MODEL, liveTime = 500, timeUnit = TimeUnit.MILLISECONDS)
    public Page<CsgoBattleRoom> getRoomList(BattleRoomDto.GetRoomListReq getRoomListReq) {
        BizDict battleStatusDict = bizDictService.getDictByTag("csgo_battle_status");
        QueryWrapper queryWrapper = QueryWrapper.create(new CsgoBattleRoom()
                .setBattleModel(getRoomListReq.getBattleModel())
                .setStatus(battleStatusDict.getValueByAlias("battle_wait")));
        Page<CsgoBattleRoom> roomPage = page(Page.of(getRoomListReq.getPageNumber(), getRoomListReq.getPageSize()), queryWrapper);
        // 如果redis中有房间缓存: redis房间信息替换结果集,否则数据库查询
        List<CsgoBattleRoom> battleRooms = roomPage.getRecords().stream().map(
                room -> {
                    room = selfProxy.getInfo(room.getId());
                    return room;
                }
        ).toList();
        roomPage.setRecords(battleRooms);
        return roomPage;
    }
}
