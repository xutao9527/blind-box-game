package com.bbg.gateway.feign;

import com.bbg.model.sys.SysTenant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "admin-server")
public interface TenantSearchService {
    @GetMapping("/sysTenant/all")
    List<SysTenant> all();
}
