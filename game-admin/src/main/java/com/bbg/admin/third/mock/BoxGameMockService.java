package com.bbg.admin.third.mock;


import com.bbg.core.box.dto.LoginDto;
import com.bbg.core.entity.ApiRet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "box-app-server")
public interface BoxGameMockService {

    ApiRet<LoginDto.LoginRes> login(@RequestBody LoginDto.LoginReq loginReq);

    ApiRet<String> logout();
}
