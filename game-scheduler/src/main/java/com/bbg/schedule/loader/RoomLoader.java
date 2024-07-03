package com.bbg.schedule.loader;


import com.alibaba.fastjson2.JSONObject;
import com.bbg.core.box.config.AutoBattleConfig;
import com.bbg.core.service.csgo.CsgoConfigService;
import com.bbg.core.service.sys.SysTenantService;
import com.bbg.core.utils.SpringUtil;
import com.bbg.core.utils.TenantUtil;
import com.bbg.model.csgo.CsgoConfig;
import com.bbg.model.sys.SysTenant;
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

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomLoader {
    public final ScheduleService scheduleService;
    public final CsgoConfigService csgoConfigService;
    public final SysTenantService sysTenantService;

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
        List<SysTenant> sysTenants = sysTenantService.list().stream()
                .filter(sysTenant -> sysTenant.getEnable() && sysTenant.getParentId() != null).toList();
        for (SysTenant sysTenant : sysTenants) {
            try {
                TenantUtil.setTenantId(sysTenant.getId());
                CsgoConfig csgoConfig = csgoConfigService.getConfigByNameAlias("auto_battle_config");
                AutoBattleConfig battleConfig = null;
                if (csgoConfig != null) {
                    try {
                        battleConfig = JSONObject.parseObject(csgoConfig.getValue(), AutoBattleConfig.class);
                    } catch (Exception e) {
                        log.error("autoBattleConfig parse error", e);
                    }
                }
                if (battleConfig != null) {
                    createRoomJob(battleConfig, sysTenant);
                    joinRoomJob(battleConfig, sysTenant);
                } else {
                    scheduleService.delete(JobKey.jobKey(ROOM_CREATE + "-[" + sysTenant.getTenantName() + "]", ROOM_GROUP));
                    scheduleService.delete(JobKey.jobKey(ROOM_JOIN + "-[" + sysTenant.getTenantName() + "]", ROOM_GROUP));
                }
            } finally {
                TenantUtil.clear();
            }
        }
    }

    /**
     * 创建房间任务
     */
    public void createRoomJob(AutoBattleConfig battleConfig, SysTenant sysTenant) {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(ROOM_CREATE + "-[" + sysTenant.getTenantName() + "]", ROOM_GROUP)
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInMilliseconds(battleConfig.getCreateRoomInterval())
                        .repeatForever())
                .build();
        JobDetail jobDetail = JobBuilder.newJob(RoomJob.CreateRoom.class)
                .withIdentity(ROOM_CREATE + "-[" + sysTenant.getTenantName() + "]", ROOM_GROUP)
                .usingJobData("maxWaitRoomSize", battleConfig.getMaxWaitRoomSize())
                .usingJobData("tenantId", sysTenant.getId())
                .build();
        scheduleService.save(jobDetail, trigger);
    }

    /**
     * 加入房间任务
     */
    public void joinRoomJob(AutoBattleConfig battleConfig, SysTenant sysTenant) {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(ROOM_JOIN + "-[" + sysTenant.getTenantName() + "]", ROOM_GROUP)
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInMilliseconds(battleConfig.getJoinRoomInterval())
                        .repeatForever())
                .build();
        JobDetail jobDetail = JobBuilder.newJob(RoomJob.JoinRoom.class)
                .withIdentity(ROOM_JOIN + "-[" + sysTenant.getTenantName() + "]", ROOM_GROUP)
                .usingJobData("tenantId", sysTenant.getId())
                .build();
        scheduleService.save(jobDetail, trigger);
    }
}
