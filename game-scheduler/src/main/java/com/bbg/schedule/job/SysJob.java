package com.bbg.schedule.job;

import com.bbg.core.utils.SpringUtil;
import com.bbg.schedule.loader.RollLoader;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class SysJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        RollLoader rollLoader = SpringUtil.getBean(RollLoader.class);
        // rollLoader.loadJob();
        // log.info("SysJob");
    }
}
