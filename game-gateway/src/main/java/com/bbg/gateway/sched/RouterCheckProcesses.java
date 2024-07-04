package com.bbg.gateway.sched;

import com.bbg.model.sys.SysTenant;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RouterCheckProcesses {

    private static final Logger log = LoggerFactory.getLogger(RouterCheckProcesses.class);
    private final RouteDefinitionLocator routeDefinitionLocator;
    private final RouteDefinitionWriter routeDefinitionWriter;
    private final WebClient.Builder webClientBuilder;
    private final StripPrefixGatewayFilterFactory stripPrefixGatewayFilterFactory;

    @Scheduled(initialDelay = 2000, fixedRate = 5000) // 每隔 1 秒检查一次配置
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

        routeDefinitionLocator.getRouteDefinitions()
                .doOnNext(route -> {
                    if (route.getUri().toString().equals("lb://box-app-server")) {
                        System.out.println("Route Id: " + route.getId() );
                        System.out.println("Route Predicates: " + route.getPredicates() );
                        System.out.println("Route Uri: " + route.getUri() );
                        System.out.println("Route Filters: " + route.getFilters() );
                        System.out.println();
                    }
                })
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
                                routeDefinition.setFilters(Collections.singletonList(
                                        new FilterDefinition("StripPrefix=1")
                                ));
                                return routeDefinitionWriter.save(Mono.just(routeDefinition));
                            }
                        }))
                .then();


    }
}
