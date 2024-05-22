package com.bbg.schedule.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.bbg.core.constants.ServicesConst;

public class IdTool {
    private final static Snowflake snowflake = IdUtil.getSnowflake(ServicesConst.SCHEDULER_APP.ordinal());

    public static long nextId(){
        return snowflake.nextId();
    }
}
