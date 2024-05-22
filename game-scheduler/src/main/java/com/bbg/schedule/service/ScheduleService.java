package com.bbg.schedule.service;

import com.bbg.schedule.entity.JobInfo;
import org.quartz.SchedulerException;

import java.util.List;

public interface ScheduleService {
    List<JobInfo> getAll() throws SchedulerException;
}
