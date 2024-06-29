package com.bbg.admin.base.controller.csgo;

import com.bbg.admin.base.BaseController;
import com.bbg.model.csgo.CsgoBattleRoomBox;
import com.bbg.core.service.csgo.CsgoBattleRoomBoxService;
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
 * 对战房间用户 控制层。
 *
 * @author bbg
 * @since 2024-05-13
 */
@Tag(name = "对战房间箱子接口")
@RequestMapping("/csgoBattleRoomBox")
public class BaseCsgoBattleRoomBoxController extends BaseController<CsgoBattleRoomBox, CsgoBattleRoomBoxService> {
    @Autowired
    protected  CsgoBattleRoomBoxService csgoBattleRoomBoxService;

    @PostMapping("save")
    @Operation(summary = "保存", description = "保存")
    public ApiRet<Boolean> save(@RequestBody CsgoBattleRoomBox model) {
        return ApiRet.buildOk(csgoBattleRoomBoxService.save(model));
    }

    @GetMapping("remove/{id}")
    @Operation(summary = "根据主键删除", description = "根据主键删除")
    public ApiRet<Boolean> remove(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(csgoBattleRoomBoxService.removeById(id));
    }

    @PostMapping("update")
    @Operation(summary = "根据主键更新", description = "根据主键更新")
    public ApiRet<Boolean> update(@RequestBody @Parameter(description = "业务主键") CsgoBattleRoomBox model) {
        return ApiRet.buildOk(csgoBattleRoomBoxService.updateById(model));
    }

    @GetMapping("getInfo/{id}")
    @Operation(summary = "根据主键获取", description = "根据主键获取")
    public ApiRet<CsgoBattleRoomBox> getInfo(@PathVariable(name = "id") @Parameter(description = "业务主键") Serializable id) {
        return ApiRet.buildOk(csgoBattleRoomBoxService.getById(id));
    }

    @PostMapping("list")
    @Operation(summary = "查询所有", description = "查询所有")
    public ApiRet<List<CsgoBattleRoomBox>> list(@RequestBody CsgoBattleRoomBox model) {
        return ApiRet.buildOk(csgoBattleRoomBoxService.list(QueryWrapper.create(model)));
    }

    @PostMapping("page")
    @Operation(summary = "分页查询", description = "分页查询")
    public ApiRet<Page<CsgoBattleRoomBox>> page(@RequestBody @Parameter(description = "分页信息") ReqParams<CsgoBattleRoomBox> reqParams) {
        // Entity 转查询条件
        SqlOperators operators = SqlOperators.of().set(CsgoBattleRoomBox::getId, SqlOperator.EQUALS);
        QueryWrapper queryWrapper = reqParams.getQueryEntity() == null ? QueryWrapper.create() : QueryWrapper.create(reqParams.getQueryEntity(), operators);
        queryWrapper = super.buildQueryWrapper(queryWrapper, reqParams.getQueryEntity());
        return ApiRet.buildOk(csgoBattleRoomBoxService.page(reqParams.getPage(), queryWrapper));
    }
}