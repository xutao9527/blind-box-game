package com.bbg.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class QuartzJobInfo {

    // 任务类名
    String jobClassName;

    // 任务名称
    String jobName;

    // 任务分组
    String jobGroup;

    // 触发器类型: [SimpleTrigger] [CronTrigger]
    String triggerType;

    // Cron表达式: CronTrigger
    String cronExpression;

    // 执行频率[SimpleTrigger]
    Long repeatInterval;

    // 租户ID
    Long tenantId;

    // 租户名称
    String tenantName;

    // 任务描述
    String description;

    // 下次执行时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date nextExecuteTime;

    // 开始时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date startTime;

    // 结束时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date endTime;
}
