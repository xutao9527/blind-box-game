package com.bbg.schedule.job;

import com.bbg.core.service.csgo.CsgoRollService;
import com.bbg.core.utils.SpringUtil;
import com.bbg.core.utils.TenantUtil;
import com.bbg.model.csgo.CsgoRoll;
import com.bbg.schedule.loader.RollLoader;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class RollJob  {

    public static class Check implements Job{
        @Override
        public void execute(JobExecutionContext jobExecutionContext)  {
            RollLoader rollLoader = SpringUtil.getBean(RollLoader.class);
            rollLoader.reLoadJob();
        }
    }

    public static class Online implements Job{
        @Override
        public void execute(JobExecutionContext jobExecutionContext)  {
            JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            long tenantId = jobDataMap.getLongValue("tenantId");
            try {
                TenantUtil.setTenantId(tenantId);
                CsgoRoll csgoRoll = new CsgoRoll();
                csgoRoll.setId(jobDataMap.getLongValue("rollId"));
                CsgoRollService csgoRollService = SpringUtil.getBean(CsgoRollService.class);
                csgoRollService.onlineRoll(csgoRoll);
            } finally {
                TenantUtil.clear();
            }
        }
    }

    public static class Offline implements Job{
        @Override
        public void execute(JobExecutionContext jobExecutionContext)  {
            JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            long tenantId = jobDataMap.getLongValue("tenantId");
            try {
                TenantUtil.setTenantId(tenantId);
                CsgoRoll csgoRoll = new CsgoRoll();
                csgoRoll.setId(jobDataMap.getLongValue("rollId"));
                CsgoRollService csgoRollService = SpringUtil.getBean(CsgoRollService.class);
                csgoRollService.offlineRoll(csgoRoll);
            } finally {
                TenantUtil.clear();
            }
        }
    }
}
