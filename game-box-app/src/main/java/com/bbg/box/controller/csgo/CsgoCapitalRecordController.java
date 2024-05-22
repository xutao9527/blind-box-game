package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.model.csgo.CsgoCapitalRecord;
import com.bbg.core.service.csgo.CsgoCapitalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 资金流水 控制层。
 *
 * @author bbg
 * @since 2024-05-04
 */
@RestController
@Tag(name = "资金流水接口")
@RequestMapping("/csgoCapitalRecord")
@RequiredArgsConstructor
public class CsgoCapitalRecordController extends BaseController<CsgoCapitalRecord> {

    public final CsgoCapitalRecordService csgoCapitalRecordService;


}