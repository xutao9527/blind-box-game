package com.bbg.third.engine;

import com.bbg.model.biz.BizPayPlatform;
import com.bbg.model.biz.BizUser;

import java.math.BigDecimal;

public interface PayEngine {

    Object execCall(BizUser bizUser, BizPayPlatform bizPayPlatform, BigDecimal money);

    Object execCallback();

    Object execOrderQuery();

}
