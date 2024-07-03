package com.bbg.core.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.bbg.core.constrans.ServicesConst;


public class IdTool {
    private final static Snowflake snowflake = IdUtil.getSnowflake();

    public static long nextId(){
        return snowflake.nextId();
    }
}
