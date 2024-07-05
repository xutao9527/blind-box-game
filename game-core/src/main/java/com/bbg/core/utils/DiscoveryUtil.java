package com.bbg.core.utils;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DiscoveryUtil {
    public final DiscoveryClient discoveryClient;
    private static DiscoveryClient staticDiscoveryClient;

    @PostConstruct
    public void init() {
        DiscoveryUtil.staticDiscoveryClient = discoveryClient;
    }

    // 判断是否是某服务调用
    public static boolean isServiceCall(String serviceId) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return staticDiscoveryClient.getInstances(serviceId).stream().anyMatch(instance -> instance.getHost().equals(request.getRemoteHost()));
    }

    // 判断是否内部服务调用
    public static boolean isInnerCall() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        for (String serviceId : staticDiscoveryClient.getServices()) {
            return staticDiscoveryClient.getInstances(serviceId).stream().anyMatch(instance -> instance.getHost().equals(request.getRemoteHost()));
        }
        return false;
    }
}
