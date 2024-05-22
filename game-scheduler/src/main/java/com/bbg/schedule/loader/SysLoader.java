package com.bbg.schedule.loader;

import com.bbg.schedule.job.SysJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.quartz.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SysLoader {
    public final Scheduler scheduler;

    public void loadJob() throws SchedulerException {
        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("sys_trigger")
                .withSchedule(CronScheduleBuilder
                                .cronSchedule("0/10 * * * * ?"))
                .build();

        JobDetail jobDetail = JobBuilder.newJob(SysJob.class)
                .withIdentity("SysJob")
                .build();





        scheduler.scheduleJob(jobDetail,trigger1);

    }
}
