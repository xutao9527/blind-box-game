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
    public final RollLoader rollLoader;

    public void loadJob() throws SchedulerException {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("SysJobtrigger")
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("0/1 * * * * ?"))
                .endAt(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();

        JobDetail jobDetail = JobBuilder.newJob(SysJob.class)
                .withIdentity("SysJobDetail")
                .build();
        scheduler.scheduleJob(jobDetail,trigger);
    }
}
