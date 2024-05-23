package com.bbg.schedule.service;

import com.bbg.schedule.entity.JobInfo;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import java.util.List;

public interface ScheduleService {
    List<JobInfo> getAll() throws SchedulerException;

    boolean save(JobDetail jobDetail, Trigger trigger);

    boolean delete(JobKey jobKey) ;
}
