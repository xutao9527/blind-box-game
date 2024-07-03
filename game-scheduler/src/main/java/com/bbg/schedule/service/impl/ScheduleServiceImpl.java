package com.bbg.schedule.service.impl;

import com.bbg.core.entity.QuartzJobInfo;
import com.bbg.core.service.sys.SysTenantService;
import com.bbg.model.sys.SysTenant;
import com.bbg.schedule.service.ScheduleService;
import com.esotericsoftware.minlog.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    public final Scheduler scheduler;
    public final SysTenantService sysTenantService;

    public List<QuartzJobInfo> getAll() throws SchedulerException {

        Map<Long,SysTenant> sysTenantMap = sysTenantService.list().stream().collect(Collectors.toMap(SysTenant::getId, sysTenant -> sysTenant));

        List<QuartzJobInfo> jobInfoList = new ArrayList<>();
        GroupMatcher<TriggerKey> matcher = GroupMatcher.anyTriggerGroup();
        Set<TriggerKey> triggerKeys = scheduler.getTriggerKeys(matcher);
        for (TriggerKey triggerKey : triggerKeys) {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(JobKey.jobKey(trigger.getJobKey().getName(), trigger.getJobKey().getGroup()));
            Object tenantId = jobDetail.getJobDataMap().get("tenantId");
            tenantId = tenantId == null ? null : Long.parseLong(tenantId.toString());
            SysTenant sysTenant = sysTenantMap.get(tenantId);
            QuartzJobInfo jobInfo = new QuartzJobInfo()
                    .setJobName(triggerKey.getName())
                    .setJobGroup(triggerKey.getGroup())
                    .setDescription(jobDetail.getDescription())
                    .setJobClassName(jobDetail.getJobClass().getName())
                    .setTenantId(sysTenant == null ? null : sysTenant.getId())
                    .setTenantName(sysTenant == null ? null : sysTenant.getTenantName());


            if (trigger instanceof CronTrigger cronTrigger) {
                jobInfo.setNextExecuteTime(cronTrigger.getNextFireTime());
                jobInfo.setStartTime(cronTrigger.getStartTime());
                jobInfo.setEndTime(cronTrigger.getEndTime());
                jobInfo.setTriggerType("CronTrigger");
                jobInfo.setCronExpression(cronTrigger.getCronExpression());
            } else if (trigger instanceof SimpleTrigger simpleTrigger) {
                jobInfo.setNextExecuteTime(simpleTrigger.getNextFireTime());
                jobInfo.setStartTime(simpleTrigger.getStartTime());
                jobInfo.setEndTime(simpleTrigger.getEndTime());
                jobInfo.setTriggerType("SimpleTrigger");
                jobInfo.setRepeatInterval(simpleTrigger.getRepeatInterval());
            }
            jobInfoList.add(jobInfo);
        }
        return jobInfoList;
    }

    public boolean save(JobDetail jobDetail, Trigger trigger) {
        try {
            Trigger existTrigger = scheduler.getTrigger(trigger.getKey());
            // 判断是否存在
            if (existTrigger != null) {
                // 判断CronTrigger表达式是否一致
                if (trigger instanceof CronTrigger cronTrigger && existTrigger instanceof CronTrigger existCronTrigger) {
                    var cronExpression = cronTrigger.getCronExpression();
                    var existCronExpression = existCronTrigger.getCronExpression();
                    if (cronExpression.equals(existCronExpression)) {
                        // log.info("CronTrigger exist: {}.{}", trigger.getKey().getName(), trigger.getKey().getGroup());
                        return true;
                    } else {
                        delete(existTrigger.getJobKey());
                    }
                    // 判断SimpleTrigger是否一致
                } else if (trigger instanceof SimpleTrigger simpleTrigger && existTrigger instanceof SimpleTrigger existsimpleTrigger) {
                    if (simpleTrigger.getRepeatInterval() == existsimpleTrigger.getRepeatInterval()) {
                        // log.info("SimpleTrigger exist: {}.{}", trigger.getKey().getName(), trigger.getKey().getGroup());
                        return true;
                    } else {
                        delete(existTrigger.getJobKey());
                    }
                } else {
                    delete(existTrigger.getJobKey());
                }
            }
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (Exception e) {
            Log.error("scheduleJob",e);
        }
        return false;
    }

    public boolean delete(JobKey jobKey) {
        try {
            scheduler.deleteJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            Log.error("deleteJob",e);
        }
        return false;
    }
}
