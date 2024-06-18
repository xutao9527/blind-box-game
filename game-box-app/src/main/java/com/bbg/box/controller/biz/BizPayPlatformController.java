package com.bbg.box.controller.biz;

import com.bbg.box.base.BaseController;
import com.bbg.box.third.pay.PayService;
import com.bbg.core.box.dto.BoxDto;
import com.bbg.model.biz.BizPayPlatform;
import com.bbg.core.service.biz.BizPayPlatformService;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.ReqParams;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoBox;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.constant.SqlOperator;
import com.mybatisflex.core.query.SqlOperators;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@Tag(name = "支付接口")
@RequestMapping("/bizPayPlatform")
@RequiredArgsConstructor
public class BizPayPlatformController extends BaseController<BizPayPlatform> {
    public final PayService payService;
    public final BizPayPlatformService bizPayPlatformService;

    @GetMapping("list")
    @Operation(summary = "支付列表", description = "支付列表")
    public ApiRet<List<Map>> list() {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(
                        QueryMethods.column(BizPayPlatform::getPayCode),
                        QueryMethods.column(BizPayPlatform::getPayImageUrl),
                        QueryMethods.column(BizPayPlatform::getPayName),
                        QueryMethods.column(BizPayPlatform::getPayAmountLimit)
                ).from(BizPayPlatform.class)
                .where(BizPayPlatform::getEnable).eq(true)
                .orderBy(BizPayPlatform::getSort).asc();
        List<Map> bizPayPlatforms = bizPayPlatformService.listAs(queryWrapper, Map.class);
        return ApiRet.buildOk(bizPayPlatforms);
    }

    @GetMapping("call")
    @Operation(summary = "发起支付请求", description = "发起支付请求")
    public ApiRet<Object> call(
            @RequestParam("payCode") @Parameter(description = "支付编码") @NotNull String payCode,
            @RequestParam("money") @Parameter(description = "支付金额") @NotNull BigDecimal money
    ) {
        return payService.call(payCode, money);
    }
}