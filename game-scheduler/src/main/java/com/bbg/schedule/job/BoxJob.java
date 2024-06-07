package com.bbg.schedule.job;

import com.bbg.core.utils.SpringUtil;
import com.bbg.schedule.loader.BoxLoader;
import com.bbg.schedule.loader.RollLoader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class BoxJob {

    public static class Check implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext)  {
            BoxLoader boxLoader = SpringUtil.getBean(BoxLoader.class);
            boxLoader.reLoadJob();
        }
    }

    public static class OpenBox implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext)  {
            System.out.println("开箱");
        }
    }
}
