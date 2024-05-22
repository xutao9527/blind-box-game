package com.bbg.schedule.controller;

import com.bbg.core.entity.ApiRet;
import com.bbg.schedule.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "调度任务接口")
@RequestMapping("/task")
public class ScheduleController extends BaseController {
    @Autowired
    Scheduler scheduler;

    @GetMapping("list")
    @Operation(description = "所有任务")
    public ApiRet list(){
        return ApiRet.buildOk(null);
    }
}
