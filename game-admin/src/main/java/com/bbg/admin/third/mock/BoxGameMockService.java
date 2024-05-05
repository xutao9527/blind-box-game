package com.bbg.admin.third.mock;


import com.bbg.admin.third.zbt.ZBTHttpService;
import com.bbg.core.box.dto.BoxDto;
import com.bbg.core.box.dto.LoginDto;
import com.bbg.core.entity.ApiRet;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Data;
import lombok.Synchronized;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "box-app-server",configuration = BoxGameMockService.GameMockInterceptor.class)
public interface BoxGameMockService {

    @PostMapping("/bizUser/login")
    ApiRet<LoginDto.LoginRes> login(@RequestBody LoginDto.LoginReq loginReq);

    @GetMapping("/bizUser/logout")
    ApiRet<String> logout();

    @PostMapping("/csgoBox/list")
    ApiRet<BoxDto.GetBoxRes> list(@RequestBody BoxDto.GetBoxReq model);

    @PostMapping("/csgoBox/open")
    ApiRet<BoxDto.OpenBoxRes> openBox(@RequestBody BoxDto.OpenBoxReq model);

    class GameMockInterceptor implements RequestInterceptor {
        public static volatile String token = null;
        @Override
        public void apply(RequestTemplate requestTemplate) {
            if (token != null) {
                requestTemplate.header("token",token);
            }
        }
    }
}
