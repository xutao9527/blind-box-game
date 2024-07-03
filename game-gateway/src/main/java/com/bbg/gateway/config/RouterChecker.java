package com.bbg.gateway.config;

import com.bbg.gateway.feign.TenantSearchService;
import com.bbg.model.sys.SysTenant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RouterChecker {

    public final RouteDefinitionLocator routeDefinitionLocator;
    public final TenantSearchService tenantSearchService;

    @Scheduled(fixedRate = 1000) // 每隔 1 秒检查一次配置
    public void checkRouter() {
        // List<SysTenant> sysTenantList = tenantSearchService.all();
        // System.out.println("sysTenantList = " + sysTenantList);
        routeDefinitionLocator.getRouteDefinitions().subscribe(routeDefinition -> {
            System.out.println("routeDefinition.getId() = " + routeDefinition.getId());
            System.out.println("routeDefinition.getUri() = " + routeDefinition.getUri());
            System.out.println("routeDefinition.getMetadata() = " + routeDefinition.getMetadata());
            System.out.println("routeDefinition.getFilters() = " + routeDefinition.getFilters());
            System.out.println("routeDefinition.getPredicates() = " + routeDefinition.getPredicates());
            System.out.println();
        });
        System.out.println("check router");
    }
}
