package com.bbg.admin.controller.biz;

import com.bbg.admin.base.controller.biz.BaseBizDataController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

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

}