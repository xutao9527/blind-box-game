package com.bbg.schedule.service.impl;

import com.bbg.schedule.entity.JobInfo;
import com.bbg.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    public final Scheduler scheduler;

    public List<JobInfo> getAll() throws SchedulerException {
        List<JobInfo> jobInfoList = new ArrayList<>();
        GroupMatcher<TriggerKey> matcher = GroupMatcher.anyTriggerGroup();
        Set<TriggerKey> triggerKeys = scheduler.getTriggerKeys(matcher);
        for (TriggerKey triggerKey : triggerKeys) {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(JobKey.jobKey(triggerKey.getName(), triggerKey.getGroup()));
            JobInfo jobInfo = new JobInfo()
                    .setJobName(triggerKey.getName())
                    .setJobGroup(triggerKey.getGroup())
                    .setDescription(jobDetail.getDescription());
            if (trigger instanceof CronTrigger cronTrigger) {
                jobInfo.setCronExpression(cronTrigger.getCronExpression());
            }
            jobInfoList.add(jobInfo);
        }
        return jobInfoList;
    }
}
