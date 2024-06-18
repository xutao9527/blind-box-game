package com.bbg.third.engine.js;

import com.bbg.model.biz.BizPayPlatform;
import com.bbg.model.biz.BizUser;
import com.bbg.third.engine.PayEngine;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ScriptPayEngine implements PayEngine {

    @Override
    public Object execCall(BizUser bizUser, BizPayPlatform bizPayPlatform, BigDecimal money) {
        return null;
    }

    @Override
    public Object execCallback() {
        return null;
    }

    @Override
    public Object execOrderQuery() {
        return null;
    }


    public void execScript() {
        System.out.println("JSPayEngine execScript");
    }
}
