package com.bbg.admin.base.controller.biz;

import com.bbg.admin.base.BaseController;
import com.bbg.model.biz.BizDictDetail;
import com.bbg.admin.service.biz.BizDictDetailService;
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
 * 系统字典详情 控制层。
 *
 * @author bbg
 * @since 2024-04-27
 */
@Tag(name = "系统字典详情接口")
@RequestMapping("/bizDictDetail")
public class BaseBizDictDetailController extends BaseController<BizDictDetail, BizDictDetailService> {
    @Autowired
    protected  BizDictDetailService bizDictDetailService;

    @PostMapping("save")
    @Operation(description = "保存")
    public ApiRet<Boolean> save(@RequestBody BizDictDetail model) {
        return ApiRet.buildOk(bizDictDetailService.save(model));
    }

    @GetMapping("remove/{id}")
    @Operation(description = "根据主键删除")
    public ApiRet<Boolean> remove(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(bizDictDetailService.removeById(id));
    }

    @PostMapping("update")
    @Operation(description = "根据主键更新")
    public ApiRet<Boolean> update(@RequestBody @Parameter(description = "业务主键") BizDictDetail model) {
        return ApiRet.buildOk(bizDictDetailService.updateById(model));
    }

    @GetMapping("getInfo/{id}")
    @Operation(description = "根据主键获取")
    public ApiRet<BizDictDetail> getInfo(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(bizDictDetailService.getById(id));
    }

    @PostMapping("list")
    @Operation(description = "查询所有")
    public ApiRet<List<BizDictDetail>> list(@RequestBody BizDictDetail model) {
        return ApiRet.buildOk(bizDictDetailService.list(QueryWrapper.create(model)));
    }

    @PostMapping("page")
    @Operation(description = "分页查询")
    public ApiRet<Page<BizDictDetail>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<BizDictDetail> reqParams) {
        // Entity 转查询条件
        SqlOperators operators = SqlOperators.of().set(BizDictDetail::getId, SqlOperator.EQUALS);
        QueryWrapper queryWrapper = reqParams.getQueryEntity() == null ? QueryWrapper.create() : QueryWrapper.create(reqParams.getQueryEntity(), operators);
        queryWrapper = super.buildQueryWrapper(queryWrapper, reqParams.getQueryEntity());
        return ApiRet.buildOk(bizDictDetailService.page(reqParams.getPage(), queryWrapper));
    }
}