package com.bbg.third.feign;

import com.bbg.core.feign.WebSocketService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "socket-server")
public interface WebSocketServiceFeign extends WebSocketService {

}
