package com.bbg.admin.controller.csgo;

import com.bbg.admin.base.controller.csgo.BaseCsgoGoodsController;
import com.bbg.admin.third.zbt.ZBTDataSyncService;
import com.bbg.core.annotation.RedisLock;
import com.bbg.core.entity.ApiRet;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Csgo商品表 控制层。
 *
 * @author bbg
 * @since 2024-04-29
 */
@RestController
@Tag(name = "Csgo商品表接口")
@RequestMapping("/csgoGoods")
@RequiredArgsConstructor
public class CsgoGoodsController extends BaseCsgoGoodsController {

    public final ZBTDataSyncService zbtDataSyncService;

    @GetMapping("syncData")
    @Operation(description = "同步ZBT商品")
    public ApiRet<Boolean> syncData() {
        return ApiRet.buildOk(zbtDataSyncService.syncData());
    }
}