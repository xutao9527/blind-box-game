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

   public String token = null;

    @GetMapping("getToken")
    @Operation(summary = "获得Token", description = "获得Token")
    public ApiRet<String> getToken() {
        if(token == null) {
            return ApiRet.buildNo(null, "token为空");
        }
        return ApiRet.buildOk(token);
    }


    @GetMapping("setToken")
    @Operation(summary = "设置Token", description = "设置Token")
    public void setToken(@RequestParam String token) {
        this.token = token;
    }
}
