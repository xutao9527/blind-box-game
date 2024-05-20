package com.bbg.admin.controller.mock;

import com.bbg.admin.third.mock.BoxGameMockService;
import com.bbg.core.box.dto.BoxDto;
import com.bbg.core.box.dto.DreamDto;
import com.bbg.core.box.dto.LoginDto;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.csgo.CsgoBox;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Csgo游戏模拟 控制层。
 *
 * @author bbg
 * @since 2024-04-29
 */
@RestController
@Tag(name = "Csgo游戏模拟接口")
@RequestMapping("/boxGameMock")
@RequiredArgsConstructor
public class BoxGameMockController {

    public final BoxGameMockService boxGameMockService;

    @GetMapping("getInfo")
    @Operation(description = "获得用户信息")
    public ApiRet<LoginDto.LoginRes> getInfo() {
        return boxGameMockService.getInfo();
    }

    @PostMapping("login")
    @Operation(description = "用户登录")
    public ApiRet<LoginDto.LoginRes> login(@RequestBody LoginDto.LoginReq loginReq) {
        ApiRet<LoginDto.LoginRes> apiRet = boxGameMockService.login(loginReq);
        if (apiRet.isOk()) {
            BoxGameMockService.GameMockInterceptor.token = apiRet.getData().getToken();
        }
        return apiRet;
    }

    @PostMapping("logout")
    @Operation(description = "用户登出")
    public ApiRet<String> logout() {
        ApiRet<String> apiRet = boxGameMockService.logout();
        if (apiRet.isOk()) {
            BoxGameMockService.GameMockInterceptor.token = null;
        }
        return apiRet;
    }

    @PostMapping("list")
    @Operation(description = "盲盒列表")
    public ApiRet<List<CsgoBox>> list(@RequestBody BoxDto.GetBoxReq model) {
        return boxGameMockService.list(model);
    }

    @PostMapping("open")
    @Operation(description = "开盲盒")
    public ApiRet<BoxDto.OpenBoxRes> openBox(@RequestBody BoxDto.OpenBoxReq model) {
        return boxGameMockService.openBox(model);
    }

    @PostMapping("dreamList")
    @Operation(description = "追梦列表")
    public ApiRet<DreamDto.DreamListRes> list(@RequestBody DreamDto.DreamListReq model) {
        return boxGameMockService.dreamList(model);
    }

    @PostMapping("dreamGood")
    @Operation(description = "进行追梦")
    public ApiRet<DreamDto.DreamGoodRes> dreamGood(@RequestBody DreamDto.DreamGoodReq model) {
        return boxGameMockService.dreamGood(model);
    }
}
