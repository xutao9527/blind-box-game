package com.bbg.core.feign.mock;

import com.bbg.core.box.dto.BoxDto;
import com.bbg.core.box.dto.DreamDto;
import com.bbg.core.box.dto.LoginDto;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.csgo.CsgoBox;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "box-app-server", configuration = BoxGameMockService.GameMockInterceptor.class)
public interface BoxGameMockService {

    @PostMapping("/bizUser/login")
    ApiRet<LoginDto.LoginRes> login(@RequestBody LoginDto.LoginPwdReq loginReq);

    @GetMapping("/bizUser/logout")
    ApiRet<String> logout();

    @GetMapping("/bizUser/getInfo")
    ApiRet<LoginDto.LoginRes> getInfo();

    @PostMapping("/csgoBox/list")
    ApiRet<List<CsgoBox>> list(@RequestBody BoxDto.GetBoxReq model);

    @PostMapping("/csgoBox/open")
    ApiRet<BoxDto.OpenBoxRes> openBox(@RequestBody BoxDto.OpenBoxReq model);

    @PostMapping("/csgoBox/dreamList")
    ApiRet<DreamDto.DreamListRes> dreamList(@RequestBody DreamDto.DreamListReq model);

    @PostMapping("/csgoBox/dreamGood")
    ApiRet<DreamDto.DreamGoodRes> dreamGood(@RequestBody DreamDto.DreamGoodReq model);

    class GameMockInterceptor implements RequestInterceptor {
        public static volatile String token = null;

        @Override
        public void apply(RequestTemplate requestTemplate) {
            if (token != null) {
                requestTemplate.header("token", token);
            }
        }
    }
}
