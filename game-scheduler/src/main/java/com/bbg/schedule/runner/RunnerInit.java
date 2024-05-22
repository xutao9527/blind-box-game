package com.bbg.schedule.runner;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RunnerInit implements ApplicationRunner {
    @Autowired
    Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("启动后执行",scheduler);
    }
}
