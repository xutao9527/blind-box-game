package com.bbg.socket.sched;

import com.bbg.model.sys.SysTenant;
import com.bbg.socket.handler.BoxWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TenantDynamicRouteCheckProcesses {
    private final WebClient.Builder webClientBuilder;

    @Scheduled(initialDelay = 500, fixedRate = 600000) // 每隔 5 秒检查一次配置
    public void fetchTenantData() {
        String url = "http://admin-server/sysTenant/getSubTenant";
        WebClient webClient = webClientBuilder.build();
        webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(SysTenant.class)
                .collectList()
                .doOnNext(this::processData)
                .doOnError(e -> {
                    log.error("获取租户信息失败:", e);
                })
                .onErrorReturn(new ArrayList<>())
                .subscribe();
    }

    private void processData(List<SysTenant> sysTenants) {
        sysTenants.forEach(sysTenant -> {
            if (!BoxWebSocketHandler.codeTenantIdMap.containsKey(sysTenant.getTenantCode())) {
                BoxWebSocketHandler.codeTenantIdMap.put(sysTenant.getTenantCode(), sysTenant.getId());
            }
        });
    }
}
