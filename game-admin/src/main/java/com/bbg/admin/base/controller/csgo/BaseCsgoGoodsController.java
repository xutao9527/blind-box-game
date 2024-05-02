package com.bbg.admin.base.controller.csgo;

import com.bbg.admin.base.BaseController;
import com.bbg.model.csgo.CsgoGoods;
import com.bbg.admin.service.csgo.CsgoGoodsService;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.ReqParams;
import com.bbg.model.sys.SysRole;
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
 * Csgo商品表 控制层。
 *
 * @author bbg
 * @since 2024-04-29
 */
@Tag(name = "Csgo商品表接口")
@RequestMapping("/csgoGoods")
public class BaseCsgoGoodsController extends BaseController<CsgoGoods, CsgoGoodsService> {
    @Autowired
    protected  CsgoGoodsService csgoGoodsService;

    @PostMapping("save")
    @Operation(description = "保存")
    public ApiRet<Boolean> save(@RequestBody CsgoGoods model) {
        return ApiRet.buildOk(csgoGoodsService.save(model));
    }

    @GetMapping("remove/{id}")
    @Operation(description = "根据主键删除")
    public ApiRet<Boolean> remove(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(csgoGoodsService.removeById(id));
    }

    @PostMapping("update")
    @Operation(description = "根据主键更新")
    public ApiRet<Boolean> update(@RequestBody @Parameter(description = "业务主键") CsgoGoods model) {
        return ApiRet.buildOk(csgoGoodsService.updateById(model));
    }

    @GetMapping("getInfo/{id}")
    @Operation(description = "根据主键获取")
    public ApiRet<CsgoGoods> getInfo(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(csgoGoodsService.getById(id));
    }

    @PostMapping("list")
    @Operation(description = "查询所有")
    public ApiRet<List<CsgoGoods>> list(@RequestBody CsgoGoods model) {
        return ApiRet.buildOk(csgoGoodsService.list(QueryWrapper.create(model)));
    }

    @PostMapping("page")
    @Operation(description = "分页查询")
    public ApiRet<Page<CsgoGoods>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<CsgoGoods> reqParams) {
        // Entity 转查询条件
        SqlOperators operators = SqlOperators.of()
                .set(CsgoGoods::getId, SqlOperator.EQUALS)
                .set(CsgoGoods::getItemName, SqlOperator.LIKE)
                .set(CsgoGoods::getMarketHashName,SqlOperator.LIKE);
        QueryWrapper queryWrapper = reqParams.getQueryEntity() == null ? QueryWrapper.create() : QueryWrapper.create(reqParams.getQueryEntity(), operators);
        queryWrapper = super.buildQueryWrapper(queryWrapper, reqParams.getQueryEntity());
        return ApiRet.buildOk(csgoGoodsService.page(reqParams.getPage(), queryWrapper));
    }
}