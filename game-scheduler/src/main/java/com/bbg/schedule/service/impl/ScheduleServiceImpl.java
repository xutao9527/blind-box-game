package com.bbg.schedule.service.impl;

import com.bbg.core.entity.JobInfo;
import com.bbg.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    public final Scheduler scheduler;

    public List<JobInfo> getAll() throws SchedulerException {
        List<JobInfo> jobInfoList = new ArrayList<>();
        GroupMatcher<TriggerKey> matcher = GroupMatcher.anyTriggerGroup();
        Set<TriggerKey> triggerKeys = scheduler.getTriggerKeys(matcher);
        for (TriggerKey triggerKey : triggerKeys) {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(JobKey.jobKey(trigger.getJobKey().getName(), trigger.getJobKey().getGroup()));
            JobInfo jobInfo = new JobInfo()
                    .setJobName(triggerKey.getName())
                    .setJobGroup(triggerKey.getGroup())
                    .setDescription(jobDetail.getDescription())
                    .setJobClassName(jobDetail.getJobClass().getName());
            if (trigger instanceof CronTrigger cronTrigger) {
                jobInfo.setNextExecuteTime(cronTrigger.getNextFireTime());
                jobInfo.setCronExpression(cronTrigger.getCronExpression());
                jobInfo.setStartTime(cronTrigger.getStartTime());
                jobInfo.setEndTime(cronTrigger.getEndTime());
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
                        log.info("CronTrigger exist: {}.{}", trigger.getKey().getName(), trigger.getKey().getGroup());
                        return true;
                    } else {
                        delete(existTrigger.getJobKey());
                    }
                // 判断SimpleTrigger是否一致
                } else if(trigger instanceof SimpleTrigger simpleTrigger && existTrigger instanceof SimpleTrigger existsimpleTrigger) {
                    if (simpleTrigger.getRepeatInterval() == existsimpleTrigger.getRepeatInterval()) {
                        log.info("SimpleTrigger exist: {}.{}", trigger.getKey().getName(), trigger.getKey().getGroup());
                        return true;
                    }
                    else {
                        delete(existTrigger.getJobKey());
                    }
                }
                else {
                    delete(existTrigger.getJobKey());
                }
            }
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public boolean delete(JobKey jobKey) {
        try {
            scheduler.deleteJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
