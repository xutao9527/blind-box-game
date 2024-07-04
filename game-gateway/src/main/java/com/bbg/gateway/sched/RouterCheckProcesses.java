package com.bbg.gateway.sched;

import com.bbg.model.sys.SysTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
// @RequiredArgsConstructor
public class RouterCheckProcesses {

    //private final RouteDefinitionLocator routeDefinitionLocator;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public RouterCheckProcesses(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Scheduled(initialDelay = 2000, fixedRate = 5000) // 每隔 1 秒检查一次配置
    public void fetchTenantData() {
        String url = "http://admin-server/sysTenant/all";
        WebClient webClient = webClientBuilder.build();
        webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(SysTenant.class).collectList()
                .doOnSuccess(this::processData)
                .subscribe();
    }

    private  void processData(List<SysTenant> sysTenants) {
        sysTenants.forEach(sysTenant -> {
            System.out.println("sysTenant = " + sysTenant);
        });
       // System.out.println("sysTenants = " + sysTenants);
    }
}
