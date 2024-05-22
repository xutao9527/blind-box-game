package com.bbg.schedule.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class JobInfo {

    String jobClassName;

    String jobName;

    String jobGroup;

    String description;

    String cronExpression;

    Date startTime;

    Date endTime;
}
