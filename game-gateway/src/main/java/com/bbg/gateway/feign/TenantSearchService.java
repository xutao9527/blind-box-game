package com.bbg.gateway.feign;

import com.bbg.model.sys.SysTenant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "admin-server",path = "/sysTenant")
public interface TenantSearchService {
    @GetMapping("all")
    List<SysTenant> all();
}
