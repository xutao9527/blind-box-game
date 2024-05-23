package com.bbg.admin.controller.biz;

import com.bbg.admin.base.controller.biz.BaseBizDataController;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.biz.BizData;
import com.esotericsoftware.minlog.Log;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * 业务数据 控制层。
 *
 * @author bbg
 * @since 2024-05-23
 */
@RestController
@Tag(name = "业务数据接口")
@RequestMapping("/bizData")
public class BizDataController extends BaseBizDataController {
    @PostMapping("saveBatch")
    @Operation(description = "批量保存")
    public ApiRet<Boolean> save(@RequestBody List<BizData> modelList) {
        boolean result = false;
        try {
            result = bizDataService.saveOrUpdateBatch(modelList);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
        return ApiRet.buildOk(result);
    }
}