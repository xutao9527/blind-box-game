package com.bbg.admin.third.scheduler;

import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.JobInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "scheduler-server")
public interface ScheduleService {

    @GetMapping("/task/list")
    ApiRet<List<JobInfo>> list();

}
