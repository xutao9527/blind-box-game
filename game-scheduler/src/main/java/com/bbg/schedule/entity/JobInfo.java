package com.bbg.schedule.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date startTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date endTime;
}
