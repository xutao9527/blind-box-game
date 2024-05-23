package com.bbg.schedule.loader;

import cn.hutool.cron.CronTimer;
import cn.hutool.cron.CronUtil;
import com.bbg.core.service.biz.BizDictService;
import com.bbg.core.service.csgo.CsgoRollService;
import com.bbg.model.biz.BizDict;
import com.bbg.model.csgo.CsgoRoll;
import com.bbg.schedule.job.RollJob;
import com.bbg.schedule.job.SysJob;
import com.bbg.schedule.service.ScheduleService;
import com.bbg.schedule.util.CronTool;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RollLoader {

    public final CsgoRollService csgoRollService;
    public final BizDictService bizDictService;
    public final ScheduleService scheduleService;


    public void loadJob(){
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("Roll-Check")
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("0/5 * * * * ?"))
                .build();

        JobDetail jobDetail = JobBuilder.newJob(RollJob.Check.class)
                .withIdentity("Roll-Check")
                .build();
        scheduleService.save(jobDetail,trigger);
    }

    public void reLoadJob() {
        BizDict rollModelDict = bizDictService.getDictByTag("csgo_roll_model");
        BizDict rollStatusDict = bizDictService.getDictByTag("csgo_roll_status");
        QueryWrapper queryWrapper = QueryWrapper.create()
                .ne(CsgoRoll::getStatus, rollStatusDict.getValueByAlias("roll_offline"));                               // 查询没有下架的撸房
        List<CsgoRoll> csgoRollList = csgoRollService.list(queryWrapper);

        csgoRollList.forEach(roll -> {
            if (roll.getEnable()) {                                                                                     // 启用状态
                if (roll.getRollModel().equals(rollModelDict.getValueByAlias("end_time_model"))) {                      // 时间模式
                    if (roll.getStatus().equals(rollStatusDict.getValueByAlias("roll_wait_online"))) {                  // 待上架(上架 下架 任务)
                        OnlineJob(roll);
                        OfflineJob(roll);
                    } else {                                                                                            // 已上架(下架 任务)
                        OfflineJob(roll);
                    }
                } else if (roll.getRollModel().equals(rollModelDict.getValueByAlias("people_number_model"))) {          // 人数模式
                    OnlineJob(roll);                                                                                    // 待上架(上架 任务)
                }
            } else {                                                                                                    // 停用状态
                scheduleService.delete(JobKey.jobKey("Roll-Online-" + roll.getId().toString()));                  // 删除任务
                scheduleService.delete(JobKey.jobKey("Roll-Offline-" + roll.getId().toString()));                 // 删除任务
            }
        });
    }


    /**
     * 上架任务
     *
     * @param roll
     */
    public void OnlineJob(CsgoRoll roll) {
        var currentTime = LocalDateTime.now();
        if (currentTime.isBefore(roll.getStartTime())) {                                                                // 当前时间 < 开始时间
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("Roll-Online-" + roll.getId().toString())
                    .withSchedule(CronScheduleBuilder
                            .cronSchedule(CronTool.convertToCron(roll.getStartTime())))
                    .build();
            JobDetail jobDetail = JobBuilder.newJob(RollJob.Online.class)
                    .withIdentity(roll.getId().toString())
                    .build();
            scheduleService.save(jobDetail, trigger);
        } else {                                                                                                        // 当前时间 > 开始时间
            log.error("roll online overtime:{}[{}]", roll.getRollTitle(), roll.getId());                                // 房间任务过期了
        }
    }

    /**
     * 下架任务
     *
     * @param roll
     */
    public void OfflineJob(CsgoRoll roll) {
        var currentTime = LocalDateTime.now();
        if (currentTime.isBefore(roll.getStartTime())) {                                                                // 当前时间 < 结束时间
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("Roll-Offline-" + roll.getId().toString())
                    .withSchedule(CronScheduleBuilder
                            .cronSchedule(CronTool.convertToCron(roll.getStartTime())))
                    .build();
            JobDetail jobDetail = JobBuilder.newJob(RollJob.Online.class)
                    .withIdentity(roll.getId().toString())
                    .build();
            scheduleService.save(jobDetail, trigger);
        } else{                                                                                                         // 当前时间 > 结束时间
            log.error("roll online overtime:{}[{}]", roll.getRollTitle(), roll.getId());                                // 房间任务过期了
        }


    }

}
