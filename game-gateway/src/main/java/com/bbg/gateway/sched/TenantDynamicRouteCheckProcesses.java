package com.bbg.gateway.sched;

import com.bbg.model.sys.SysTenant;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TenantDynamicRouteCheckProcesses {

    private final RouteDefinitionLocator routeDefinitionLocator;
    private final RouteDefinitionWriter routeDefinitionWriter;
    private final WebClient.Builder webClientBuilder;
    private final StripPrefixGatewayFilterFactory stripPrefixGatewayFilterFactory;

    @Scheduled(initialDelay = 500, fixedRate = 5000) // 每隔 5 秒检查一次配置
    public void fetchTenantData() {
        String url = "http://admin-server/sysTenant/getSubTenant";
        WebClient webClient = webClientBuilder.build();
        webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(SysTenant.class)
                .collectList()
                .flatMap(this::processData)
                .subscribe();
    }

    private Mono<Void> processData(List<SysTenant> sysTenants) {
        return Flux.fromIterable(sysTenants)
                .flatMap(tenant -> routeDefinitionLocator.getRouteDefinitions()
                        .filter(route -> route.getId().equals(tenant.getTenantCode()))
                        .hasElements()
                        .flatMap(exists -> {
                            if (exists) {
                                return Mono.empty(); // 路由已存在，不做任何操作
                            } else {
                                // 动态添加路由
                                RouteDefinition routeDefinition = new RouteDefinition();
                                routeDefinition.setId(tenant.getTenantCode());
                                routeDefinition.setUri(URI.create("lb://box-app-server"));
                                routeDefinition.setPredicates(Collections.singletonList(
                                        new PredicateDefinition("Path=/" + tenant.getTenantCode() + "/**")
                                ));

                                FilterDefinition filterDefinition = new FilterDefinition("StripPrefixHeader=1");
                                routeDefinition.setFilters(Collections.singletonList(
                                        filterDefinition
                                ));
                                return routeDefinitionWriter.save(Mono.just(routeDefinition));
                            }
                        }))
                //.then(routeDefinitionLocator.getRouteDefinitions().collectList().doOnNext(this::printRoutes).then());
                .then();
    }

    private void printRoutes(List<RouteDefinition> routes) {
        System.out.println("Current Routes:");
        routes.forEach(route -> System.out.println(route.toString()));
        // routes.forEach(route -> {
        //     System.out.println("Route Id: " + route.getId());
        //     System.out.println("Route Predicates: " + route.getPredicates());
        //     System.out.println("Route Uri: " + route.getUri());
        //     System.out.println("Route Filters: " + route.getFilters());
        // });

    }
}
