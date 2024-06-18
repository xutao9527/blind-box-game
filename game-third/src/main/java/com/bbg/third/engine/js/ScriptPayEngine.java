package com.bbg.third.engine.js;

import com.bbg.model.biz.BizPayPlatform;
import com.bbg.model.biz.BizUser;
import com.bbg.third.engine.PayEngine;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ScriptPayEngine implements PayEngine {

    @Override
    public Object execCall(BizUser bizUser, BizPayPlatform bizPayPlatform, BigDecimal money) {
        try(Context context = Context.create()) {
            context.getBindings("js").putMember("bizUser", bizUser);
            context.getBindings("js").putMember("bizPayPlatform", bizPayPlatform);
            context.getBindings("js").putMember("money", money);
            Value value = context.eval("js", bizPayPlatform.getCallEngine());

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
