package com.bbg.admin.base.controller.csgo;

import com.bbg.admin.base.BaseController;
import com.bbg.model.csgo.CsgoCapitalRecord;
import com.bbg.core.service.csgo.CsgoCapitalRecordService;
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
 * 资金流水 控制层。
 *
 * @author bbg
 * @since 2024-05-05
 */
@Tag(name = "资金流水接口")
@RequestMapping("/csgoCapitalRecord")
public class BaseCsgoCapitalRecordController extends BaseController<CsgoCapitalRecord, CsgoCapitalRecordService> {
    @Autowired
    protected  CsgoCapitalRecordService csgoCapitalRecordService;

    @PostMapping("save")
    @Operation(summary = "保存", description = "保存")
    public ApiRet<Boolean> save(@RequestBody CsgoCapitalRecord model) {
        return ApiRet.buildOk(csgoCapitalRecordService.save(model));
    }

    @GetMapping("remove/{id}")
    @Operation(summary = "根据主键删除", description = "根据主键删除")
    public ApiRet<Boolean> remove(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(csgoCapitalRecordService.removeById(id));
    }

    @PostMapping("update")
    @Operation(summary = "根据主键更新", description = "根据主键更新")
    public ApiRet<Boolean> update(@RequestBody @Parameter(description = "业务主键") CsgoCapitalRecord model) {
        return ApiRet.buildOk(csgoCapitalRecordService.updateById(model));
    }

    @GetMapping("getInfo/{id}")
    @Operation(summary = "根据主键获取", description = "根据主键获取")
    public ApiRet<CsgoCapitalRecord> getInfo(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(csgoCapitalRecordService.getById(id));
    }

    @PostMapping("list")
    @Operation(summary = "查询所有", description = "查询所有")
    public ApiRet<List<CsgoCapitalRecord>> list(@RequestBody CsgoCapitalRecord model) {
        return ApiRet.buildOk(csgoCapitalRecordService.list(QueryWrapper.create(model)));
    }

    @PostMapping("page")
    @Operation(summary = "分页查询", description = "分页查询")
    public ApiRet<Page<CsgoCapitalRecord>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<CsgoCapitalRecord> reqParams) {
        // Entity 转查询条件
        SqlOperators operators = SqlOperators.of().set(CsgoCapitalRecord::getId, SqlOperator.EQUALS);
        QueryWrapper queryWrapper = reqParams.getQueryEntity() == null ? QueryWrapper.create() : QueryWrapper.create(reqParams.getQueryEntity(), operators);
        queryWrapper = super.buildQueryWrapper(queryWrapper, reqParams.getQueryEntity());
        queryWrapper.orderBy(CsgoCapitalRecord::getId,false);
        return ApiRet.buildOk(csgoCapitalRecordService.page(reqParams.getPage(), queryWrapper));
    }
}