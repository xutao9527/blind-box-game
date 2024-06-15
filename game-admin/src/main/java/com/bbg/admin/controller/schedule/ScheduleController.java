package com.bbg.admin.controller.schedule;

import com.bbg.admin.third.scheduler.ScheduleService;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.QuartzJobInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "调度任务接口")
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    public final ScheduleService scheduleService;

    @Operation(summary = "调度任务列表", description = "调度任务列表")
    @GetMapping("list")
    public ApiRet<List<QuartzJobInfo>> list(){
        return scheduleService.list();
    }
}
