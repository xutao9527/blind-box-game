package com.bbg.admin.base.controller.biz;

import com.bbg.admin.base.BaseController;
import com.bbg.core.service.biz.BizUserService;
import com.bbg.model.biz.BizUser;

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
 * 业务用户 控制层。
 *
 * @author bbg
 * @since 2024-04-26
 */
@Tag(name = "业务用户接口")
@RequestMapping("/bizUser")
public class BaseBizUserController extends BaseController<BizUser, BizUserService> {
    @Autowired
    protected  BizUserService bizUserService;

    // @PostMapping("save")
    // @Operation(summary = "保存", description = "保存")
    // public ApiRet<Boolean> save(@RequestBody BizUser model) {
    //     return ApiRet.buildOk(bizUserService.save(model));
    // }

    @GetMapping("remove/{id}")
    @Operation(summary = "根据主键删除", description = "根据主键删除")
    public ApiRet<Boolean> remove(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(bizUserService.removeById(id));
    }

    // @PostMapping("update")
    // @Operation(summary = "根据主键更新", description = "根据主键更新")
    // public ApiRet<Boolean> update(@RequestBody @Parameter(description = "业务主键") BizUser model) {
    //     return ApiRet.buildOk(bizUserService.updateById(model));
    // }

    @GetMapping("getInfo/{id}")
    @Operation(summary = "根据主键获取", description = "根据主键获取")
    public ApiRet<BizUser> getInfo(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(bizUserService.getById(id));
    }

    @PostMapping("list")
    Operation(summary = "查询所有", description = "查询所有")
    public ApiRet<List<BizUser>> list(@RequestBody BizUser model) {
        return ApiRet.buildOk(bizUserService.list(QueryWrapper.create(model)));
    }

    @PostMapping("page")
    Operation(summary = "分页查询", description = "分页查询")
    public ApiRet<Page<BizUser>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<BizUser> reqParams) {
        // Entity 转查询条件
        SqlOperators operators = SqlOperators.of().set(BizUser::getId, SqlOperator.EQUALS);
        QueryWrapper queryWrapper = reqParams.getQueryEntity() == null ? QueryWrapper.create() : QueryWrapper.create(reqParams.getQueryEntity(), operators);
        queryWrapper = super.buildQueryWrapper(queryWrapper, reqParams.getQueryEntity());
        return ApiRet.buildOk(bizUserService.page(reqParams.getPage(), queryWrapper));
    }
}