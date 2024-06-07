package com.bbg.schedule.loader;

import com.bbg.core.service.csgo.CsgoConfigService;
import com.bbg.model.csgo.CsgoConfig;
import com.bbg.schedule.job.BoxJob;
import com.bbg.schedule.job.RollJob;
import com.bbg.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoxLoader {
    public final ScheduleService scheduleService;
    public final CsgoConfigService csgoConfigService;

    public static final String BOX_CHECK = "盲盒-实时检查";
    public static final String BOX_OEPN = "盲盒-开箱";

    public static final String BOX_GROUP = "BOX";

    public void loadJob() {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(BOX_CHECK, BOX_GROUP)
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("0/5 * * * * ?"))
                .build();
        JobDetail jobDetail = JobBuilder.newJob(BoxJob.Check.class)
                .withIdentity(BOX_CHECK, BOX_GROUP)
                .build();
        scheduleService.save(jobDetail, trigger);
    }

    public void reLoadJob() {
        CsgoConfig csgoConfig = csgoConfigService.getConfigByNameAlias("open_box_interval");
        if(csgoConfig!=null){
            long openBoxInterval = Long.parseLong(csgoConfig.getValue());
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(BOX_OEPN, BOX_GROUP)
                    .withSchedule(SimpleScheduleBuilder
                            .simpleSchedule()
                            .withIntervalInMilliseconds(openBoxInterval)
                            .repeatForever())
                    .build();
            JobDetail jobDetail = JobBuilder.newJob(BoxJob.OpenBox.class)
                    .withIdentity(BOX_OEPN, BOX_GROUP)
                    .build();
            scheduleService.save(jobDetail, trigger);
        }else{
            scheduleService.delete(JobKey.jobKey(BOX_OEPN, BOX_GROUP));
        }
    }

}
