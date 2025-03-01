package com.bbg.admin.controller.biz;

import com.bbg.admin.base.controller.biz.BaseBizDataController;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.biz.BizData;
import com.esotericsoftware.minlog.Log;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 业务数据 控制层。
 *
 * @author bbg
 * @since 2024-05-23
 */
@RestController
@Tag(name = "业务数据接口")
@RequestMapping("/bizData")
@Slf4j
public class BizDataController extends BaseBizDataController {

    private boolean isRunning = false;

    @PostMapping("saveBatch")
    @Operation(summary = "批量保存", description = "批量保存")
    public ApiRet<Boolean> save(@RequestBody List<BizData> modelList) {
        boolean isOk = false;
        try {
            List<BizData> distinctModelList = modelList.stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toMap(
                                    BizData::getValue, // 根据value属性去重
                                    Function.identity(),
                                    (existing, replacement) -> existing, // 保留第一个出现的元素
                                    LinkedHashMap::new // 使用LinkedHashMap来保持顺序
                            ),
                            map -> new ArrayList<>(map.values())
                    ));
            if (!isRunning) {
                isOk = true;
                isRunning = true;
                CompletableFuture.runAsync(() -> {
                    try {
                        long start = System.currentTimeMillis();
                        bizDataService.saveBatch(distinctModelList);
                        log.info("执行时间:{}ms", System.currentTimeMillis() - start);
                    } catch (Exception e) {
                        Log.error("saveBatch",e);
                    } finally {
                        isRunning = false;
                    }

                });
            }
        } catch (Exception e) {
            Log.error("saveBatch",e);
        }
        return ApiRet.buildOk(isOk);
    }
}