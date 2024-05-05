package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.core.box.dto.BoxDto;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoBox;
import com.bbg.box.service.csgo.CsgoBoxService;
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
 * CSGO箱子 控制层。
 *
 * @author bbg
 * @since 2024-05-03
 */
@RestController
@Tag(name = "CSGO箱子接口")
@RequestMapping("/csgoBox")
public class CsgoBoxController extends BaseController<CsgoBox, CsgoBoxService> {
    @Autowired
    protected  CsgoBoxService csgoBoxService;

    @PostMapping("list")
    @Operation(description = "获得盲盒列表")
    public ApiRet<BoxDto.GetBoxRes> list(@RequestBody BoxDto.GetBoxReq model) {
        List<CsgoBox> csgoBoxes = csgoBoxService.getBoxesByType(model.getType());
        return ApiRet.buildOk(new BoxDto.GetBoxRes().setCsgoBoxes(csgoBoxes));
    }

    @PostMapping("open")
    @Operation(description = "打开盲盒")
    public ApiRet<BoxDto.OpenBoxRes> openBox(@RequestBody BoxDto.OpenBoxReq model) {
        BizUser bizUser = getCurrentUser();
        csgoBoxService.openBox(bizUser,model.getBoxId());
        return ApiRet.buildOk(new BoxDto.OpenBoxRes());
    }
}