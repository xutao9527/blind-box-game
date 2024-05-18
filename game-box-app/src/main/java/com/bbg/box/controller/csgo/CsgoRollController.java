package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.core.box.dto.RollDto;
import com.bbg.model.csgo.CsgoBattleRoom;
import com.bbg.model.csgo.CsgoRoll;
import com.bbg.box.service.csgo.CsgoRollService;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.ReqParams;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.constant.SqlOperator;
import com.mybatisflex.core.query.SqlOperators;
import jakarta.validation.constraints.NotNull;
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
 * Roll房间 控制层。
 *
 * @author bbg
 * @since 2024-05-16
 */
@RestController
@Tag(name = "Roll房间接口")
@RequestMapping("/csgoRoll")
public class CsgoRollController extends BaseController<CsgoRoll, CsgoRollService> {
    @Autowired
    protected  CsgoRollService csgoRollService;



    @PostMapping("getRollList")
    @Operation(description = "获取撸房列表")
    public ApiRet<Page<CsgoRoll>> getRollList(@RequestBody RollDto.GetRollListReq model){
        return ApiRet.buildOk(csgoRollService.getRollList(model));
    }

    @GetMapping("join")
    @Operation(description = "加入撸房")
    public ApiRet<CsgoRoll> join(@NotNull @RequestParam("rollId") Long rollId){
        return csgoRollService.join(rollId);
    }

    @GetMapping("getRoll")
    @Operation(description = "获得撸房信息")
    public ApiRet<CsgoRoll> getRoll(@NotNull @RequestParam("rollId") Long rollId){
        return ApiRet.buildOk(csgoRollService.getById(rollId));
    }

}