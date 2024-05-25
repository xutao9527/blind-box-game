package com.bbg.admin.base.controller.csgo;

import com.bbg.admin.base.BaseController;
import com.bbg.model.csgo.CsgoOpenBoxLog;
import com.bbg.core.service.csgo.CsgoOpenBoxLogService;
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
 * CSGO开箱日志 控制层。
 *
 * @author bbg
 * @since 2024-05-25
 */
@Tag(name = "CSGO开箱日志接口")
@RequestMapping("/csgoOpenBoxLog")
public class BaseCsgoOpenBoxLogController extends BaseController<CsgoOpenBoxLog, CsgoOpenBoxLogService> {
    @Autowired
    protected CsgoOpenBoxLogService csgoOpenBoxLogService;

    @PostMapping("save")
    @Operation(description = "保存")
    public ApiRet<Boolean> save(@RequestBody CsgoOpenBoxLog model) {
        return ApiRet.buildOk(csgoOpenBoxLogService.save(model));
    }

    @GetMapping("remove/{id}")
    @Operation(description = "根据主键删除")
    public ApiRet<Boolean> remove(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(csgoOpenBoxLogService.removeById(id));
    }

    @PostMapping("update")
    @Operation(description = "根据主键更新")
    public ApiRet<Boolean> update(@RequestBody @Parameter(description = "业务主键") CsgoOpenBoxLog model) {
        return ApiRet.buildOk(csgoOpenBoxLogService.updateById(model));
    }

    @GetMapping("getInfo/{id}")
    @Operation(description = "根据主键获取")
    public ApiRet<CsgoOpenBoxLog> getInfo(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(csgoOpenBoxLogService.getById(id));
    }

    @PostMapping("list")
    @Operation(description = "查询所有")
    public ApiRet<List<CsgoOpenBoxLog>> list(@RequestBody CsgoOpenBoxLog model) {
        return ApiRet.buildOk(csgoOpenBoxLogService.list(QueryWrapper.create(model)));
    }

    @PostMapping("page")
    @Operation(description = "分页查询")
    public ApiRet<Page<CsgoOpenBoxLog>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<CsgoOpenBoxLog> reqParams) {
        // Entity 转查询条件
        SqlOperators operators = SqlOperators.of().set(CsgoOpenBoxLog::getId, SqlOperator.EQUALS);
        QueryWrapper queryWrapper = reqParams.getQueryEntity() == null ? QueryWrapper.create() : QueryWrapper.create(reqParams.getQueryEntity(), operators);
        queryWrapper = super.buildQueryWrapper(queryWrapper, reqParams.getQueryEntity());
        return ApiRet.buildOk(csgoOpenBoxLogService.page(reqParams.getPage(), queryWrapper));
    }
}