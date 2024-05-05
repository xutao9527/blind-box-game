package com.bbg.admin.controller.csgo;

import com.bbg.admin.base.controller.csgo.BaseCsgoCapitalRecordController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 资金流水 控制层。
 *
 * @author bbg
 * @since 2024-05-05
 */
@RestController
@Tag(name = "资金流水接口")
@RequestMapping("/csgoCapitalRecord")
public class CsgoCapitalRecordController extends BaseCsgoCapitalRecordController {

}