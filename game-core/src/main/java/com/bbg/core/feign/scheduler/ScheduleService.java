package com.bbg.core.feign.scheduler;

import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.QuartzJobInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "scheduler-server")
public interface ScheduleService {

    @GetMapping("/task/list")
    ApiRet<List<QuartzJobInfo>> list();

}
