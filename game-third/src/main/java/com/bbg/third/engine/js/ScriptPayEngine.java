package com.bbg.third.engine.js;

import com.bbg.core.entity.WebSocketMsg;
import com.bbg.core.feign.socket.WebSocketService;
import com.bbg.model.biz.BizPayPlatform;
import com.bbg.model.biz.BizUser;
import com.bbg.third.engine.PayEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScriptPayEngine implements PayEngine {

    public final WebSocketService webSocketService;

    @Override
    public Object execCall(BizUser bizUser, BizPayPlatform bizPayPlatform, BigDecimal money) {
        log.info(log.getClass().getName());
        webSocketService.sendAdminMessage(new WebSocketMsg().setMessage("支付请求"));
        try(Context context = Context.newBuilder().allowAllAccess(true).build()) {
            context.getBindings("js").putMember("log", log);
            // context.getBindings("js").putMember("bizPayPlatform", bizPayPlatform);
            // context.getBindings("js").putMember("money", money);
            // context.eval("js", bizPayPlatform.getCallEngine());
            context.eval("js", bizPayPlatform.getCallContent());
        } catch (Exception e) {
            log.info("execCall",e );
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
