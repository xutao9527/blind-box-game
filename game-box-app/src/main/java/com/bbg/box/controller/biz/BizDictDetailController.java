package com.bbg.box.controller.biz;

import com.bbg.box.base.BaseController;
import com.bbg.model.biz.BizDictDetail;
import com.bbg.box.service.biz.BizDictDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 系统字典详情 控制层。
 *
 * @author bbg
 * @since 2024-05-03
 */
@RestController
@Tag(name = "系统字典详情接口")
@RequestMapping("/bizDictDetail")
@RequiredArgsConstructor
public class BizDictDetailController extends BaseController<BizDictDetail> {

    public final BizDictDetailService bizDictDetailService;
}