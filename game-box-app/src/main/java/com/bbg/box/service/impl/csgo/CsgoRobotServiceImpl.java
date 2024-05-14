package com.bbg.box.service.impl.csgo;

import com.bbg.core.annotation.RedisCache;
import com.bbg.core.constants.KeyConst;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoRobot;
import com.bbg.box.mapper.csgo.CsgoRobotMapper;
import com.bbg.box.service.csgo.CsgoRobotService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机器人 服务层实现。
 *
 * @author bbg
 * @since 2024-05-13
 */
@Service
public class CsgoRobotServiceImpl extends ServiceImpl<CsgoRobotMapper, CsgoRobot> implements CsgoRobotService {
    @Override
    @RedisCache(value = "'all'" ,key = KeyConst.ROBOT_LIST)
    public List<CsgoRobot> list() {
        return this.list(QueryWrapper.create().eq(CsgoRobot::getEnable,true));
    }
}
