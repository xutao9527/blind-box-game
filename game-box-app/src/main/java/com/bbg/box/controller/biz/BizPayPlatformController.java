package com.bbg.box.controller.biz;

import com.bbg.box.base.BaseController;
import com.bbg.model.biz.BizPayPlatform;
import com.bbg.core.service.biz.BizPayPlatformService;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.ReqParams;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.constant.SqlOperator;
import com.mybatisflex.core.query.SqlOperators;
import lombok.RequiredArgsConstructor;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 支付平台管理 控制层。
 *
 * @author bbg
 * @since 2024-06-14
 */
@RestController
@Tag(name = "支付平台管理接口")
@RequestMapping("/bizPayPlatform")
@RequiredArgsConstructor
public class BizPayPlatformController extends BaseController {

    public final BizPayPlatformService bizPayPlatformService;


}