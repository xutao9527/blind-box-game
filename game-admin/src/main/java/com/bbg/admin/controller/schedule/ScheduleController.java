package com.bbg.admin.controller.schedule;

import com.bbg.admin.base.BaseController;
import com.bbg.core.feign.scheduler.ScheduleService;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.QuartzJobInfo;
import com.bbg.core.utils.TenantUtil;
import com.bbg.model.sys.SysUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@Tag(name = "调度任务接口")
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController extends BaseController {
    public final ScheduleService scheduleService;

    @Operation(summary = "调度任务列表", description = "调度任务列表")
    @GetMapping("list")
    public ApiRet<List<QuartzJobInfo>> list(){
        List<QuartzJobInfo> list = scheduleService.list().getData();
        SysUser sysUser = getCurrentUser();
        if(!sysUser.isSuperTenant()){
            list =  list.stream().filter(quartzJobInfo -> Objects.equals(quartzJobInfo.getTenantId(), sysUser.getTenantId())||quartzJobInfo.getTenantId()==null).toList();
        }
        return ApiRet.buildOk(list);
    }
}
