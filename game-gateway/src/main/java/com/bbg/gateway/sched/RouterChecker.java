package com.bbg.gateway.sched;

import com.bbg.model.sys.SysTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
// @RequiredArgsConstructor
public class RouterChecker {

    //private final RouteDefinitionLocator routeDefinitionLocator;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public RouterChecker(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Scheduled(fixedRate = 5000) // 每隔 1 秒检查一次配置
    public void fetchAndProcessData() {
        String url = "http://admin-server/sysTenant/all";
        WebClient webClient = webClientBuilder.build();
        webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(String.class).collectList()
                .doOnSuccess(this::processData)
                .subscribe();
    }

    private  void processData(List<String> sysTenants) {
        sysTenants.forEach(System.out::println);
       // System.out.println("sysTenants = " + sysTenants);
    }
}
