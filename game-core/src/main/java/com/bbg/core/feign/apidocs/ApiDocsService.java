package com.bbg.core.feign.apidocs;

import com.bbg.core.entity.ApiRet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "admin-server")
public interface ApiDocsService {

    @GetMapping("/v3/api-docs")
    Map<String,Object> getDocs();
}
