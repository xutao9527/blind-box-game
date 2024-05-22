package com.bbg.schedule.service.impl;

import com.bbg.schedule.service.ScheduleService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    Scheduler scheduler;
}
