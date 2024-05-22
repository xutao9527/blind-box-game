package com.bbg.schedule.loader;

import com.bbg.schedule.job.SysJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
                .endAt(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();

        JobDetail jobDetail1 = JobBuilder.newJob(SysJob.class)
                .withIdentity("SysJob")
                .build();

        JobDetail jobDetail2 = JobBuilder.newJob(SysJob.class)
                .withIdentity("SysJob2")
                .build();

        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("sys_trigger1")
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("0/20 * * * * ?"))
                .build();


        scheduler.scheduleJob(jobDetail1, trigger1);
        scheduler.scheduleJob(jobDetail2, trigger2);
    }
}
