package com.bbg.admin.controller.csgo;

import com.bbg.admin.base.controller.csgo.BaseCsgoRollController;
import com.bbg.core.constrans.KeyConst;
import com.bbg.core.service.biz.BizDictService;
import com.bbg.core.annotation.RedisClear;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.csgo.CsgoRoll;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;

/**
 * Roll房间 控制层。
 *
 * @author bbg
 * @since 2024-05-16
 */
@RestController
@Tag(name = "Roll房间接口")
@RequestMapping("/csgoRoll")
@RequiredArgsConstructor
public class CsgoRollController extends BaseCsgoRollController {

    public final BizDictService bizDictService;

    @Override
    @PostMapping("update")
    @RedisClear(value = "#model.id", key = KeyConst.ROLL_INFO_ID)
    public ApiRet<Boolean> update(CsgoRoll model) {
        var currentTime = LocalDateTime.now();
        var rollStatusDict = bizDictService.getDictByTag("csgo_roll_status");
        var rollModelDict = bizDictService.getDictByTag("csgo_roll_model");
        CsgoRoll roll = csgoRollService.getById(model.getId());
        if (roll != null && model.getEnable()
                && currentTime.isAfter(roll.getStartTime())
                && currentTime.isBefore(roll.getEndTime())
                && roll.getStatus().equals(rollStatusDict.getValueByAlias("roll_wait_online"))
                && roll.getRollModel().equals(rollModelDict.getValueByAlias("end_time_model"))      // 时间模式
        ) {
            model.setStatus(rollStatusDict.getValueByAlias("roll_online"));
            return ApiRet.buildOk(csgoRollService.updateById(model));
        } else if (roll != null && model.getEnable()
                && currentTime.isAfter(roll.getStartTime())
                && roll.getStatus().equals(rollStatusDict.getValueByAlias("roll_wait_online"))
                && roll.getRollModel().equals(rollModelDict.getValueByAlias("people_number_model")) // 人数模式
        ) {
            model.setStatus(rollStatusDict.getValueByAlias("roll_online"));
            return ApiRet.buildOk(csgoRollService.updateById(model));
        } else if (roll != null) {
            return ApiRet.buildOk(csgoRollService.updateById(model));
        }
        return ApiRet.buildNo("房间号不存在");
    }
}