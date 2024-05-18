package com.bbg.admin.controller.csgo;

import com.bbg.admin.base.controller.csgo.BaseCsgoRollController;
import com.bbg.admin.service.biz.BizDictService;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.csgo.CsgoRoll;
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
public class CsgoRollController extends BaseCsgoRollController {
    @Autowired
    BizDictService bizDictService;

    @Override
    public ApiRet<Boolean> update(CsgoRoll model) {
        var currentTime = LocalDateTime.now();
        var rollStatusDict = bizDictService.getDictByTag("csgo_roll_status");
        if (model.getEnable()
                && currentTime.isAfter(model.getStartTime())
                && currentTime.isBefore(model.getEndTime())
                && model.getStatus().equals(rollStatusDict.getValueByAlias("roll_wait_online"))
        ) {
            model.setStatus(rollStatusDict.getValueByAlias("roll_online"));
        }
        return ApiRet.buildOk(csgoRollService.updateById(model));
    }
}