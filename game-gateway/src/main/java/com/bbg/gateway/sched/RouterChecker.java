package com.bbg.gateway.sched;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RouterChecker {

    private final RouteDefinitionLocator routeDefinitionLocator;
    private final WebClient.Builder webBuilder;

    @Scheduled(fixedRate = 5000) // 每隔 1 秒检查一次配置
    public void checkRouter() {
        System.out.println("webBuilder = " + webBuilder);

        Mono<Object> objectMono = webBuilder
                .baseUrl("admin-server").build().get()
                .uri("/sysTenant/all")
                .exchangeToMono(clientResponse -> {
                    System.out.println("clientResponse = " + clientResponse);
                    return clientResponse.bodyToMono(Object.class);
                });
        Object block = objectMono.subscribe();
        System.out.println(block);
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
