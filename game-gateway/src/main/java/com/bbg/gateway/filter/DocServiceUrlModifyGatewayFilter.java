// package com.bbg.gateway.filter;
//
// import org.reactivestreams.Publisher;
// import org.springdoc.core.properties.SpringDocConfigProperties;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.cloud.gateway.filter.GatewayFilterChain;
// import org.springframework.cloud.gateway.filter.GlobalFilter;
// import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
// import org.springframework.core.Ordered;
// import org.springframework.core.io.buffer.DataBuffer;
// import org.springframework.core.io.buffer.DataBufferFactory;
// import org.springframework.core.io.buffer.DefaultDataBufferFactory;
// import org.springframework.http.server.reactive.ServerHttpRequest;
// import org.springframework.http.server.reactive.ServerHttpResponse;
// import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ServerWebExchange;
// import reactor.core.publisher.Flux;
// import reactor.core.publisher.Mono;
// import java.net.URI;
// import java.nio.charset.StandardCharsets;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;
//
// @Component
// public class DocServiceUrlModifyGatewayFilter implements GlobalFilter, Ordered {
//     @Autowired
//     SpringDocConfigProperties springDocConfigProperties;
//
//     @Override
//     public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//         ServerHttpRequest request = exchange.getRequest();
//         ServerHttpResponse response = exchange.getResponse();
//
//         final URI uri = request.getURI();
//         String apiDocsPath = springDocConfigProperties.getApiDocs().getPath();
//
//         if (!uri.getPath().isBlank() && uri.getPath().endsWith(apiDocsPath)) {
//             String urlString = uri.toString();
//             String gatewayUrl = urlString.substring(0, urlString.indexOf(apiDocsPath));
//             DataBufferFactory bufferFactory = response.bufferFactory();
//
//             ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
//                 @Override
//                 public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                     Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
//                     return super.writeWith(
//                             fluxBody.buffer().map(dataBuffers -> {
//                                 DataBuffer dataBuffer = new DefaultDataBufferFactory().join(dataBuffers);
//                                 byte[] content = new byte[dataBuffer.readableByteCount()];
//                                 dataBuffer.read(content);
//                                 try {
//                                     String responseData = new String(content, StandardCharsets.UTF_8);
//                                     String regex = "\"url\":\\s*\"([^\"]*)\"";
//                                     Pattern pattern = Pattern.compile(regex);
//                                     Matcher matcher = pattern.matcher(responseData);
//                                     if(matcher.find()){
//                                         String oldUrl = matcher.group(1);
//                                         responseData = responseData.replace(oldUrl, gatewayUrl);
//                                         // log.info("oldUrl ->{}", responseData);
//                                     }
//                                     byte[] newContent = responseData.getBytes(StandardCharsets.UTF_8);
//                                     response.getHeaders().setContentLength(newContent.length);
//                                     return bufferFactory.wrap(newContent);
//                                 } catch (Exception e) {
//                                     e.printStackTrace();
//                                 }
//                                 return bufferFactory.wrap(content);
//                             })
//                     );
//                 }
//             };
//             return chain.filter(exchange.mutate().response(decoratedResponse).build());
//         }
//         return chain.filter(exchange);
//     }
//
//     @Override
//     public int getOrder() {
//         return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
//     }
// }
//
