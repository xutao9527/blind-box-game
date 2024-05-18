package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.core.box.dto.BattleRoomDto;
import com.bbg.core.box.dto.RollDto;
import com.bbg.core.constants.KeyConst;
import com.bbg.model.biz.BizUser;
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
@Tag(name = "CS:GO撸房接口")
@RequestMapping("/csgoRoll")
public class CsgoRollController extends BaseController<CsgoRoll, CsgoRollService> {
    @Autowired
    protected CsgoRollService csgoRollService;

    @PostMapping("getRollList")
    @Operation(description = "获取撸房列表")
    public ApiRet<Page<CsgoRoll>> getRollList(@RequestBody RollDto.GetRollListReq model) {
        return ApiRet.buildOk(csgoRollService.getRollList(model));
    }

    @GetMapping("join")
    @Operation(description = "加入撸房")
    public ApiRet<CsgoRoll> join(@NotNull @RequestParam("rollId") Long rollId) {
        ApiRet<CsgoRoll> apiRet;
        BizUser bizUser = getCurrentUser();
        if (bizUser == null) {
            return ApiRet.buildNo("用户不存在");
        }
        try {
            apiRet = csgoRollService.joinRoll(bizUser, rollId);
        } catch (Exception e) {
            redisService.delete(KeyConst.build(KeyConst.ROLL_INFO_ID, String.valueOf(rollId)));
            apiRet = ApiRet.buildNo("加入房间异常");
        }
        return apiRet;
    }

    @GetMapping("getRoll")
    @Operation(description = "获得撸房")
    public ApiRet<CsgoRoll> getRoll(@NotNull @RequestParam("rollId") Long rollId) {
        return ApiRet.buildOk(csgoRollService.getInfo(rollId));
    }

}