package com.bbg.schedule.loader;


import com.alibaba.fastjson2.JSONObject;
import com.bbg.core.box.config.AutoBattleConfig;
import com.bbg.core.service.csgo.CsgoConfigService;
import com.bbg.core.utils.SpringUtil;
import com.bbg.model.csgo.CsgoConfig;
import com.bbg.schedule.job.RollJob;
import com.bbg.schedule.job.RoomJob;
import com.bbg.schedule.service.ScheduleService;
import com.bbg.schedule.util.CronTool;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomLoader {
    public final ScheduleService scheduleService;
    public final CsgoConfigService csgoConfigService;

    public static final String ROOM_CHECK = "对战-实时检查";
    public static final String ROOM_CREATE = "对战-创建房间";
    public static final String ROOM_JOIN = "对战-加入房间";

    public static final String ROOM_GROUP = "BATTLE";

    public void loadJob() {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(ROOM_CHECK, ROOM_GROUP)
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("0/5 * * * * ?"))
                .build();
        JobDetail jobDetail = JobBuilder.newJob(RoomJob.Check.class)
                .withIdentity(ROOM_CHECK, ROOM_GROUP)
                .build();
        scheduleService.save(jobDetail, trigger);
    }

    public void reLoadJob() {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(QueryMethods.column(CsgoConfig::getValue))
                .eq(CsgoConfig::getEnable, true)
                .eq(CsgoConfig::getNameAlias, "auto_battle_config");
        String autoBattleConfig = csgoConfigService.getOneAs(queryWrapper, String.class);
        AutoBattleConfig battleConfig = null;
        if (autoBattleConfig != null) {
            try {
                battleConfig = JSONObject.parseObject(autoBattleConfig, AutoBattleConfig.class);
            } catch (Exception e) {
                log.error("autoBattleConfig parse error", e);
            }
        }
        if (battleConfig != null) {
            createRoomJob(battleConfig);
            joinRoomJob(battleConfig);
        } else {
            scheduleService.delete(JobKey.jobKey(ROOM_CREATE, ROOM_GROUP));
            scheduleService.delete(JobKey.jobKey(ROOM_JOIN, ROOM_GROUP));
        }
    }

    /**
     * 创建房间任务
     */
    public void createRoomJob(AutoBattleConfig battleConfig) {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(ROOM_CREATE, ROOM_GROUP)
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInMilliseconds(battleConfig.getJoinRoomInterval())
                        .repeatForever())
                .build();
        JobDetail jobDetail = JobBuilder.newJob(RoomJob.CreateRoom.class)
                .withIdentity(ROOM_CREATE, ROOM_GROUP)
                .usingJobData("maxWaitRoomSize", battleConfig.getMaxWaitRoomSize())
                .build();
        scheduleService.save(jobDetail, trigger);
    }

    /**
     * 加入房间任务
     */
    public void joinRoomJob(AutoBattleConfig battleConfig) {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(ROOM_JOIN, ROOM_GROUP)
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInMilliseconds(battleConfig.getJoinRoomInterval())
                        .repeatForever())
                .build();
        JobDetail jobDetail = JobBuilder.newJob(RoomJob.JoinRoom.class)
                .withIdentity(ROOM_JOIN, ROOM_GROUP)
                .build();
        scheduleService.save(jobDetail, trigger);
    }
}
