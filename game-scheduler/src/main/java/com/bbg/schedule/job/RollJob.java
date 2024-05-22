package com.bbg.schedule.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class RollJob  {

    public static class Online implements Job{

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            log.info("Online");
        }
    }

    public static class Offline implements Job{
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            log.info("Offline");
        }
    }
}
