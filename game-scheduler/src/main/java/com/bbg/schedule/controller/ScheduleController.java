package com.bbg.schedule.controller;

import com.bbg.core.entity.ApiRet;
import com.bbg.schedule.base.BaseController;
import com.bbg.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Tag(name = "调度任务接口")
@RequestMapping("/task")
@RequiredArgsConstructor
public class ScheduleController extends BaseController {
    public final Scheduler scheduler;
    public final ScheduleService scheduleService;

    @GetMapping("list")
    @Operation(description = "所有任务")
    public ApiRet<List<Map<String,Object>>> list() throws Exception {
        List<Map<String,Object>> jobList = new ArrayList<>();
        scheduleService.getAll();
        return ApiRet.buildOk(jobList);
    }
}
