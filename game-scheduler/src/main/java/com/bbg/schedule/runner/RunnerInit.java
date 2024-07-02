package com.bbg.schedule.runner;

import com.bbg.schedule.loader.BoxLoader;
import com.bbg.schedule.loader.RollLoader;
import com.bbg.schedule.loader.RoomLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RunnerInit implements ApplicationRunner {

    public final Scheduler scheduler;

    public final RollLoader rollLoader;
    public final RoomLoader roomLoader;
    public final BoxLoader boxLoader;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        scheduler.start();
        // rollLoader.loadJob();
        // roomLoader.loadJob();
        boxLoader.loadJob();
    }
}
