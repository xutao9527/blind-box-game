package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.box.service.biz.BizDictService;
import com.bbg.box.service.csgo.CsgoBoxGoodsService;
import com.bbg.core.annotation.RedisLock;
import com.bbg.core.box.dto.BoxDto;
import com.bbg.core.box.dto.DreamDto;
import com.bbg.model.biz.BizDict;
import com.bbg.model.biz.BizDictDetail;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoBox;
import com.bbg.box.service.csgo.CsgoBoxService;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.ReqParams;
import com.bbg.model.csgo.CsgoBoxGoods;
import com.bbg.model.csgo.CsgoGoods;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.constant.SqlOperator;
import com.mybatisflex.core.query.SqlOperators;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * CSGO箱子 控制层。
 *
 * @author bbg
 * @since 2024-05-03
 */
@RestController
@RequestMapping("/csgoBox")
public class CsgoBoxController extends BaseController<CsgoBox, CsgoBoxService> {
    @Autowired
    protected CsgoBoxService csgoBoxService;
    @Autowired
    protected CsgoBoxGoodsService csgoBoxGoodsService;
    @Autowired
    protected BizDictService bizDictService;

    @PostMapping("list")
    @Tag(name = "CS:GO盲盒接口")
    @Operation(description = "获得盲盒列表")
    public ApiRet<BoxDto.GetBoxRes> list(@RequestBody @Validated BoxDto.GetBoxReq model) {
        List<CsgoBox> csgoBoxes = csgoBoxService.getBoxesByType(model.getType());
        return ApiRet.buildOk(new BoxDto.GetBoxRes().setCsgoBoxes(csgoBoxes));
    }

    @PostMapping("open")
    @Tag(name = "CS:GO盲盒接口")
    @Operation(description = "打开盲盒")
    public ApiRet<BoxDto.OpenBoxRes> openBox(@RequestBody BoxDto.OpenBoxReq model) {
        BizUser bizUser = getCurrentUser();
        BoxDto.OpenBoxRes boxRes = csgoBoxService.openBox(bizUser, model.getBoxId());
        return ApiRet.buildOk(boxRes);
    }

    @PostMapping("dreamList")
    @Tag(name = "CS:GO追梦接口")
    @Operation(description = "获得追梦商品列表")
    public ApiRet<DreamDto.DreamListRes> dreamList(@RequestBody DreamDto.DreamListReq model) {
        Page<CsgoBoxGoods> page = null;
        BizDict bizDict = bizDictService.getDictByTag("csgo_box_type");
        BizDictDetail bizDictDetail = bizDict.getBizDictDetails().stream().filter(detail -> detail.getLabelAlias().equals("dream_box")).findFirst().orElse(null);
        if (bizDictDetail != null) {
            QueryWrapper queryWrapper = QueryWrapper.create()
                    .from(CsgoBoxGoods.class)
                    .and(CsgoBoxGoods::getName).like(model.getName())
                    .and(CsgoBoxGoods::getType).eq(model.getType())
                    .in(
                            CsgoBoxGoods::getBoxId,
                            QueryWrapper.create().select(QueryMethods.column(CsgoBox::getId)).from(CsgoBox.class).where(CsgoBox::getType).eq(bizDictDetail.getValue())
                    );
            queryWrapper = super.buildQueryWrapper(queryWrapper, model);
            page = csgoBoxGoodsService.page(Page.of(model.getPageNumber(), model.getPageSize()), queryWrapper);
        }
        return ApiRet.buildOk(new DreamDto.DreamListRes().setDreamGoodsPage(page));
    }

    @PostMapping("dreamGood")
    @Tag(name = "CS:GO追梦接口")
    @Operation(description = "进行追梦")
    public ApiRet<DreamDto.DreamGoodRes> dreamGood(@RequestBody DreamDto.DreamGoodReq model) {
        BizUser bizUser = getCurrentUser();
        DreamDto.DreamGoodRes dreamGoodRes = csgoBoxService.dreamGood(bizUser, model);
        return ApiRet.buildOk(dreamGoodRes);
    }
}