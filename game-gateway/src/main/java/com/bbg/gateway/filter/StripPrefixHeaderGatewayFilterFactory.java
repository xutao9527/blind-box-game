package com.bbg.gateway.filter;

import lombok.Data;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.GatewayToStringStyler;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;

@Component
public class StripPrefixHeaderGatewayFilterFactory extends AbstractGatewayFilterFactory<StripPrefixHeaderGatewayFilterFactory.Config> {
    public static final String PARTS_KEY = "parts";

    public StripPrefixHeaderGatewayFilterFactory() {
        super(Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return List.of("parts");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest();
                ServerWebExchangeUtils.addOriginalRequestUrl(exchange, request.getURI());
                String path = request.getURI().getRawPath();
                String[] originalParts = StringUtils.tokenizeToStringArray(path, "/");
                StringBuilder newPath = new StringBuilder("/");

                for(int i = 0; i < originalParts.length; ++i) {
                    if (i >= config.getParts()) {
                        if (newPath.length() > 1) {
                            newPath.append('/');
                        }

                        newPath.append(originalParts[i]);
                    }
                }

                if (newPath.length() > 1 && path.endsWith("/")) {
                    newPath.append('/');
                }

                ServerHttpRequest newRequest = request.mutate().path(newPath.toString()).build();
                exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());
                return chain.filter(exchange.mutate().request(newRequest).build());
            }

            public String toString() {
                return GatewayToStringStyler.filterToStringCreator(StripPrefixHeaderGatewayFilterFactory.this).append("parts", config.getParts()).toString();
            }
        };
    }

    @Data
    public static class Config {
        private int parts = 1;

        public Config() {}

    }
}
