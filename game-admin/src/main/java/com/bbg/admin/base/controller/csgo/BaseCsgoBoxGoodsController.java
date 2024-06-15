package com.bbg.admin.base.controller.csgo;

import com.bbg.admin.base.BaseController;
import com.bbg.model.biz.BizDictDetail;
import com.bbg.model.csgo.CsgoBoxGoods;
import com.bbg.core.service.csgo.CsgoBoxGoodsService;
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
 * CSGO箱子商品 控制层。
 *
 * @author bbg
 * @since 2024-05-02
 */
@Tag(name = "CSGO箱子商品接口")
@RequestMapping("/csgoBoxGoods")
public class BaseCsgoBoxGoodsController extends BaseController<CsgoBoxGoods, CsgoBoxGoodsService> {
    @Autowired
    protected  CsgoBoxGoodsService csgoBoxGoodsService;

    @PostMapping("save")
    @Operation(summary = "保存", description = "保存")
    public ApiRet<Boolean> save(@RequestBody CsgoBoxGoods model) {
        return ApiRet.buildOk(csgoBoxGoodsService.save(model));
    }

    @GetMapping("remove/{id}")
    @Operation(summary = "根据主键删除", description = "根据主键删除")
    public ApiRet<Boolean> remove(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(csgoBoxGoodsService.removeById(id));
    }

    @PostMapping("update")
    @Operation(summary = "根据主键更新", description = "根据主键更新")
    public ApiRet<Boolean> update(@RequestBody @Parameter(description = "业务主键") CsgoBoxGoods model) {
        return ApiRet.buildOk(csgoBoxGoodsService.updateById(model));
    }

    @GetMapping("getInfo/{id}")
    @Operation(summary = "根据主键获取", description = "根据主键获取")
    public ApiRet<CsgoBoxGoods> getInfo(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(csgoBoxGoodsService.getById(id));
    }

    @PostMapping("list")
    Operation(summary = "查询所有", description = "查询所有")
    public ApiRet<List<CsgoBoxGoods>> list(@RequestBody CsgoBoxGoods model) {
        QueryWrapper queryWrapper = QueryWrapper.create(model).orderBy(CsgoBoxGoods::getSort, true);
        return ApiRet.buildOk(csgoBoxGoodsService.list(queryWrapper));
    }

    @PostMapping("page")
    Operation(summary = "分页查询", description = "分页查询")
    public ApiRet<Page<CsgoBoxGoods>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<CsgoBoxGoods> reqParams) {
        // Entity 转查询条件
        SqlOperators operators = SqlOperators.of().set(CsgoBoxGoods::getId, SqlOperator.EQUALS);
        QueryWrapper queryWrapper = reqParams.getQueryEntity() == null ? QueryWrapper.create() : QueryWrapper.create(reqParams.getQueryEntity(), operators);
        queryWrapper = super.buildQueryWrapper(queryWrapper, reqParams.getQueryEntity());
        return ApiRet.buildOk(csgoBoxGoodsService.page(reqParams.getPage(), queryWrapper));
    }
}