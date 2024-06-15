package com.bbg.admin.base.controller.biz;

import com.bbg.admin.base.BaseController;
import com.bbg.model.biz.BizData;
import com.bbg.core.service.biz.BizDataService;
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
 * 业务数据 控制层。
 *
 * @author bbg
 * @since 2024-05-23
 */
@Tag(name = "业务数据接口")
@RequestMapping("/bizData")
public class BaseBizDataController extends BaseController<BizData, BizDataService> {
    @Autowired
    protected BizDataService bizDataService;

    @PostMapping("save")
    @Operation(summary = "保存", description = "保存")
    public ApiRet<Boolean> save(@RequestBody BizData model) {
        return ApiRet.buildOk(bizDataService.save(model));
    }

    @GetMapping("remove/{id}")
    @Operation(summary = "根据主键删除", description = "根据主键删除")
    public ApiRet<Boolean> remove(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(bizDataService.removeById(id));
    }

    @PostMapping("update")
    @Operation(summary = "根据主键更新", description = "根据主键更新")
    public ApiRet<Boolean> update(@RequestBody @Parameter(description = "业务主键") BizData model) {
        return ApiRet.buildOk(bizDataService.updateById(model));
    }

    @GetMapping("getInfo/{id}")
    @Operation(summary = "根据主键获取", description = "根据主键获取")
    public ApiRet<BizData> getInfo(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(bizDataService.getById(id));
    }

    @PostMapping("list")
    @Operation(summary = "查询所有", description = "查询所有")
    public ApiRet<List<BizData>> list(@RequestBody BizData model) {
        return ApiRet.buildOk(bizDataService.list(QueryWrapper.create(model)));
    }

    @PostMapping("page")
    @Operation(summary = "分页查询", description = "分页查询")
    public ApiRet<Page<BizData>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<BizData> reqParams) {
        // Entity 转查询条件
        SqlOperators operators = SqlOperators.of().set(BizData::getId, SqlOperator.EQUALS);
        QueryWrapper queryWrapper = reqParams.getQueryEntity() == null ? QueryWrapper.create() : QueryWrapper.create(reqParams.getQueryEntity(), operators);
        queryWrapper = super.buildQueryWrapper(queryWrapper, reqParams.getQueryEntity());
        return ApiRet.buildOk(bizDataService.page(reqParams.getPage(), queryWrapper));
    }
}