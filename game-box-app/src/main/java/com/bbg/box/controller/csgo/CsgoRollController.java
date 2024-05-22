package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.box.service.biz.BizUserService;
import com.bbg.box.service.csgo.CsgoRollUserService;
import com.bbg.core.box.dto.RollDto;
import com.bbg.core.constants.KeyConst;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoRoll;
import com.bbg.box.service.csgo.CsgoRollService;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.csgo.CsgoRollUser;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

/**
 * Roll房间 控制层。
 *
 * @author bbg
 * @since 2024-05-16
 */
@RestController
@Tag(name = "CS:GO撸房接口")
@RequestMapping("/csgoRoll")
@RequiredArgsConstructor
public class CsgoRollController extends BaseController<CsgoRoll> {

    public final CsgoRollService csgoRollService;
    public final CsgoRollUserService csgoRollUserService;
    public final BizUserService bizUserService;

    @PostMapping("getRollList")
    @Operation(description = "获取撸房列表")
    public ApiRet<Page<CsgoRoll>> getRollList(@RequestBody RollDto.GetRollListReq model) {
        return ApiRet.buildOk(csgoRollService.getRollList(model));
    }

    @GetMapping("getRoll")
    @Operation(description = "获得撸房")
    public ApiRet<CsgoRoll> getRoll(@NotNull @RequestParam("rollId") Long rollId) {
        return ApiRet.buildOk(csgoRollService.getInfo(rollId));
    }

    @PostMapping("getRollUsers")
    @Operation(description = "获得撸房用户列表")
    public ApiRet<Page<CsgoRollUser>> getRollUsers(@RequestBody RollDto.GetRollUsersReq model) {
        Page<CsgoRollUser> page = csgoRollUserService.page(Page.of(model.getPageNumber(), model.getPageSize()), QueryWrapper.create(new CsgoRollUser().setRollId(model.getRollId())));
        return ApiRet.buildOk(page);
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

    @GetMapping("joinByUserId")
    @Operation(description = "加入撸房(用户Id-测试)")
    public ApiRet<CsgoRoll> join(@NotNull @RequestParam("rollId") Long rollId, @NotNull @RequestParam("userId") Long userId) {
        ApiRet<CsgoRoll> apiRet;
        BizUser bizUser = bizUserService.getById(userId);
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

    @GetMapping("offlineRoll")
    @Operation(description = "加入撸房(用户Id-测试)")
    public ApiRet<Boolean> offlineRoll(@NotNull @RequestParam("rollId") Long rollId) {
        CsgoRoll roll = csgoRollService.getById(rollId);
        if(roll!=null){
            return ApiRet.buildOk(csgoRollService.offlineRoll(roll));
        }
        return ApiRet.buildNo("房间不存在");
    }

}