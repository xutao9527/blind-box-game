package com.bbg.schedule.job;


import com.alibaba.fastjson2.JSONObject;
import com.bbg.core.box.config.AutoBattleConfig;
import com.bbg.core.service.csgo.CsgoConfigService;
import com.bbg.core.utils.SpringUtil;
import com.bbg.model.csgo.CsgoConfig;
import com.bbg.schedule.loader.RoomLoader;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

@Slf4j
public class RoomJob {

    public static class Check implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            RoomLoader roomLoader = SpringUtil.getBean(RoomLoader.class);
            roomLoader.reLoadJob();
        }
    }

    public static class CreateRoom implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            log.info("CreateRoom");
        }
    }

    public static class JoinRoom implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            log.info("JoinRoom");
        }
    }
}
