package com.bbg.third.engine.js;

import com.bbg.model.biz.BizPayPlatform;
import com.bbg.model.biz.BizUser;
import com.bbg.third.engine.PayEngine;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component

public class ScriptPayEngine implements PayEngine {



    @Override
    public Object execCall(BizUser bizUser, BizPayPlatform bizPayPlatform, BigDecimal money) {
        try(Context context = Context.newBuilder().allowAllAccess(true).build()) {
            context.getBindings("js").putMember("log", log);
            // context.getBindings("js").putMember("bizPayPlatform", bizPayPlatform);
            // context.getBindings("js").putMember("money", money);
            // context.eval("js", bizPayPlatform.getCallEngine());
            context.eval("js", bizPayPlatform.getCallContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
