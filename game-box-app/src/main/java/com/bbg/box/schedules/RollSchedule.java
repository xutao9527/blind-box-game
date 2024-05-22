package com.bbg.box.schedules;

import com.bbg.core.service.biz.BizDictService;
import com.bbg.core.service.csgo.CsgoRollService;
import com.bbg.model.biz.BizDict;
import com.bbg.model.csgo.CsgoRoll;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class RollSchedule {

    @Autowired
    CsgoRollService csgoRollService;

    @Autowired
    BizDictService bizDictService;

    // @Scheduled(cron = "0/1 * * * * ? ")
    // @Scheduled(fixedRate = 1000)
    public void execute() {
        log.info("{}", System.currentTimeMillis());
        BizDict rollModelDict = bizDictService.getDictByTag("csgo_roll_model");
        BizDict rollStatusDict = bizDictService.getDictByTag("csgo_roll_status");
        QueryWrapper queryWrapper = QueryWrapper.create()
                .eq(CsgoRoll::getEnable, true)
                .ne(CsgoRoll::getStatus, rollStatusDict.getValueByAlias("roll_offline"));
        List<CsgoRoll> csgoRollList = csgoRollService.list(queryWrapper);

        var currentTime = LocalDateTime.now();
        csgoRollList.forEach(roll -> {
            if (currentTime.isAfter(roll.getStartTime())                                                // 开始时间(大于)
                    && currentTime.isBefore(roll.getEndTime())                                          // 结束时间(小于)
                    && roll.getStatus().equals(rollStatusDict.getValueByAlias("roll_wait_online"))      // 状态(待上架)
                    && roll.getRollModel().equals(rollModelDict.getValueByAlias("end_time_model"))      // 时间模式
            ) {
                csgoRollService.onlineRoll(roll);
            } else if (currentTime.isAfter(roll.getEndTime())                                           // 结束时间(超过)
                    && roll.getStatus().equals(rollStatusDict.getValueByAlias("roll_online"))           // 状态(已上架)
                    && roll.getRollModel().equals(rollModelDict.getValueByAlias("end_time_model"))      // 时间模式
            ) {
                csgoRollService.offlineRoll(roll);
            } else if (currentTime.isAfter(roll.getStartTime())                                         // 开始时间(大于)
                    && roll.getStatus().equals(rollStatusDict.getValueByAlias("roll_wait_online"))      // 状态(待上架)
                    && roll.getRollModel().equals(rollModelDict.getValueByAlias("people_number_model")) // 人数模式
            ) {
                csgoRollService.onlineRoll(roll);
            }
        });
    }
}
