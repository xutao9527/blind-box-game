package com.bbg.admin.base.controller.biz;

import com.bbg.admin.base.BaseController;
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
@Tag(name = "支付平台管理接口")
@RequestMapping("/bizPayPlatform")
public class BaseBizPayPlatformController extends BaseController<BizPayPlatform, BizPayPlatformService> {
    @Autowired
    protected BizPayPlatformService bizPayPlatformService;

    @PostMapping("save")
    @Operation(description = "保存")
    public ApiRet<Boolean> save(@RequestBody BizPayPlatform model) {
        return ApiRet.buildOk(bizPayPlatformService.save(model));
    }

    @GetMapping("remove/{id}")
    @Operation(description = "根据主键删除")
    public ApiRet<Boolean> remove(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(bizPayPlatformService.removeById(id));
    }

    @PostMapping("update")
    @Operation(description = "根据主键更新")
    public ApiRet<Boolean> update(@RequestBody @Parameter(description = "业务主键") BizPayPlatform model) {
        return ApiRet.buildOk(bizPayPlatformService.updateById(model));
    }

    @GetMapping("getInfo/{id}")
    @Operation(description = "根据主键获取")
    public ApiRet<BizPayPlatform> getInfo(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(bizPayPlatformService.getById(id));
    }

    @PostMapping("list")
    @Operation(description = "查询所有")
    public ApiRet<List<BizPayPlatform>> list(@RequestBody BizPayPlatform model) {
        return ApiRet.buildOk(bizPayPlatformService.list(QueryWrapper.create(model)));
    }

    @PostMapping("page")
    @Operation(description = "分页查询")
    public ApiRet<Page<BizPayPlatform>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<BizPayPlatform> reqParams) {
        // Entity 转查询条件
        SqlOperators operators = SqlOperators.of().set(BizPayPlatform::getId, SqlOperator.EQUALS);
        QueryWrapper queryWrapper = reqParams.getQueryEntity() == null ? QueryWrapper.create() : QueryWrapper.create(reqParams.getQueryEntity(), operators);
        queryWrapper = super.buildQueryWrapper(queryWrapper, reqParams.getQueryEntity());
        return ApiRet.buildOk(bizPayPlatformService.page(reqParams.getPage(), queryWrapper));
    }
}