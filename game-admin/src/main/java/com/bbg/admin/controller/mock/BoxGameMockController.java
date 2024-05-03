package com.bbg.admin.controller.mock;

import com.bbg.admin.third.mock.BoxGameMockService;
import com.bbg.core.box.dto.LoginDto;
import com.bbg.core.entity.ApiRet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Csgo游戏模拟 控制层。
 *
 * @author bbg
 * @since 2024-04-29
 */
@RestController
@Tag(name = "Csgo游戏模拟接口")
@RequestMapping("/boxGameMock")
public class BoxGameMockController {
    @Autowired
    BoxGameMockService boxGameMockService;

    @PostMapping("login")
    @Operation(description = "用户登录")
    public ApiRet<LoginDto.LoginRes> login(@RequestBody LoginDto.LoginReq loginReq) {
        return boxGameMockService.login(loginReq);
    }
}
