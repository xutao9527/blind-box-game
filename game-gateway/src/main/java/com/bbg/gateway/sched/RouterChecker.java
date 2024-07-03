package com.bbg.gateway.sched;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component

public class RouterChecker {
    @Autowired
    private  RouteDefinitionLocator routeDefinitionLocator;
    @Autowired
    @LoadBalanced
    private  WebClient.Builder loadBalanced;

    @Scheduled(fixedRate = 5000) // 每隔 1 秒检查一次配置
    public void checkRouter() {

        String url = "lb://admin-server/sysTenant/all";
        WebClient webClient = loadBalanced.build();

        Mono<Object> objectMono = webClient
                .get()
                .uri(url)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Object.class));
        objectMono.subscribe();


        // List<SysTenant> sysTenantList = tenantSearchService.all();
        // System.out.println("sysTenantList = " + sysTenantList);
        // routeDefinitionLocator.getRouteDefinitions().subscribe(routeDefinition -> {
        //     System.out.println("routeDefinition.getId() = " + routeDefinition.getId());
        //     System.out.println("routeDefinition.getUri() = " + routeDefinition.getUri());
        //     System.out.println("routeDefinition.getMetadata() = " + routeDefinition.getMetadata());
        //     System.out.println("routeDefinition.getFilters() = " + routeDefinition.getFilters());
        //     System.out.println("routeDefinition.getPredicates() = " + routeDefinition.getPredicates());
        //     System.out.println();
        // });
        // System.out.println("check router");
    }
}
