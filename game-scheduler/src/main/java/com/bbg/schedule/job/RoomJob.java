package com.bbg.schedule.job;


import com.alibaba.fastjson2.JSONObject;
import com.bbg.core.box.config.AutoBattleConfig;
import com.bbg.core.box.dto.BattleRoomDto;
import com.bbg.core.service.biz.BizDictService;
import com.bbg.core.service.biz.BizUserService;
import com.bbg.core.service.csgo.CsgoBattleRoomBoxService;
import com.bbg.core.service.csgo.CsgoBattleRoomService;
import com.bbg.core.service.csgo.CsgoBoxService;
import com.bbg.core.service.csgo.CsgoConfigService;
import com.bbg.core.utils.IdTool;
import com.bbg.core.utils.SpringUtil;
import com.bbg.model.biz.BizDictDetail;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoBattleRoom;
import com.bbg.model.csgo.CsgoBox;
import com.bbg.model.csgo.CsgoConfig;
import com.bbg.schedule.loader.RoomLoader;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class RoomJob {
    // 线程安全的用户集合
    public static volatile List<Long> userIds = new ArrayList<>();
    // 用户类型
    public static volatile String userType;
    // 箱子类型
    public static volatile String boxType;
    // 对战模式
    public static volatile List<String> ballteModels;


    // 用户类型总数
    public static volatile long userTypeCount;
    // 随机数
    public static volatile SecureRandom random = new SecureRandom();


    public static class Check implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            RoomLoader roomLoader = SpringUtil.getBean(RoomLoader.class);
            roomLoader.reLoadJob();
        }
    }

    public static class CreateRoom implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            long maxWaitRoomSize = jobDataMap.getIntValue("maxWaitRoomSize");
            // 加载虚拟用户类型
            if (userType == null) {
                BizDictService bizDictService = SpringUtil.getBean(BizDictService.class);
                userType = bizDictService.getDictByTag("user_type").getValueByAlias("virtual_user");
            }
            // 加载虚拟用户类型
            if (boxType == null) {
                BizDictService bizDictService = SpringUtil.getBean(BizDictService.class);
                boxType = bizDictService.getDictByTag("csgo_box_type").getValueByAlias("battle_box");
            }
            // 加载虚拟用户类型
            if (ballteModels == null || ballteModels.isEmpty()) {
                BizDictService bizDictService = SpringUtil.getBean(BizDictService.class);
                ballteModels = bizDictService.getDictByTag("csgo_battle_model").getBizDictDetails().stream().map(BizDictDetail::getValue).collect(Collectors.toList());
            }
            // 检查是否有足够的空闲房间
            CsgoBattleRoomService csgoBattleRoomService = SpringUtil.getBean(CsgoBattleRoomService.class);
            List<CsgoBattleRoom> roomList = csgoBattleRoomService.getRoomList(new BattleRoomDto.GetRoomListReq().setPageSize(100)).getRecords();
            if (roomList.size() >= maxWaitRoomSize) {
                log.info("空闲房间够多,不创建房间");
                return;
            }
            // 检查虚拟用户列表数量是否需要更新
            BizUserService bizUserService = SpringUtil.getBean(BizUserService.class);
            long newUserTypeCount = bizUserService.getUserTypeCount(userType);
            if (userTypeCount != newUserTypeCount) {
                userTypeCount = newUserTypeCount;
                userIds = bizUserService.getUserIdsByType(userType);
            }
            // -------------------------------------------------------------------------------------------------------------------
            // 随机获取一个虚拟用户
            long userId = userIds.get(random.nextInt(userIds.size()));
            BizUser bizUser = bizUserService.getById(userId);
            BattleRoomDto.CreateRoomReq createRoomReq = new BattleRoomDto.CreateRoomReq();
            // 随机对战模式
            createRoomReq.setBattleModel(ballteModels.get(random.nextInt(ballteModels.size())));
            // 随机2~4人
            createRoomReq.setPeopleNumber(random.nextInt(3)+2);
            // 随机获得若干箱子
            CsgoBoxService csgoBoxService = SpringUtil.getBean(CsgoBoxService.class);
            List<CsgoBox> csgoBoxes = csgoBoxService.getBoxesByType(boxType);
            long[] boxesId = new long[random.nextInt(6)+1];
            for (int i = 0; i < boxesId.length; i++) {
                boxesId[i] = csgoBoxes.get(random.nextInt(csgoBoxes.size())).getId();
            }
            createRoomReq.setBoxesId(boxesId);
            csgoBattleRoomService.createRoom(bizUser,createRoomReq, IdTool.nextId());
        }
    }

    public static class JoinRoom implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            log.info("JoinRoom");
        }
    }
}
