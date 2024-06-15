package com.bbg.admin.base.controller.biz;

import com.bbg.admin.base.BaseController;
import com.bbg.core.service.biz.BizDictService;
import com.bbg.model.biz.BizDict;

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
 * 系统字典 控制层。
 *
 * @author bbg
 * @since 2024-04-27
 */
@Tag(name = "系统字典接口")
@RequestMapping("/bizDict")
public class BaseBizDictController extends BaseController<BizDict, BizDictService> {
    @Autowired
    protected BizDictService bizDictService;

    @PostMapping("save")
    @Operation(summary = "保存", description = "保存")
    public ApiRet<Boolean> save(@RequestBody BizDict model) {
        return ApiRet.buildOk(bizDictService.save(model));
    }

    @GetMapping("remove/{id}")
    @Operation(summary = "根据主键删除", description = "根据主键删除")
    public ApiRet<Boolean> remove(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(bizDictService.removeById(id));
    }

    @PostMapping("update")
    @Operation(summary = "根据主键更新", description = "根据主键更新")
    public ApiRet<Boolean> update(@RequestBody @Parameter(description = "业务主键") BizDict model) {
        return ApiRet.buildOk(bizDictService.updateById(model));
    }

    @GetMapping("getInfo/{id}")
    @Operation(summary = "根据主键获取", description = "根据主键获取")
    public ApiRet<BizDict> getInfo(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(bizDictService.getById(id));
    }

    @PostMapping("list")
    Operation(summary = "查询所有", description = "查询所有")
    public ApiRet<List<BizDict>> list(@RequestBody BizDict model) {
        return ApiRet.buildOk(bizDictService.list(QueryWrapper.create(model)));
    }

    @PostMapping("page")
    Operation(summary = "分页查询", description = "分页查询")
    public ApiRet<Page<BizDict>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<BizDict> reqParams) {
        // Entity 转查询条件
        SqlOperators operators = SqlOperators.of().set(BizDict::getId, SqlOperator.EQUALS);
        QueryWrapper queryWrapper = reqParams.getQueryEntity() == null ? QueryWrapper.create() : QueryWrapper.create(reqParams.getQueryEntity(), operators);
        queryWrapper = super.buildQueryWrapper(queryWrapper, reqParams.getQueryEntity());
        return ApiRet.buildOk(bizDictService.page(reqParams.getPage(), queryWrapper));
    }
}