package com.bbg.core.aop;


import com.bbg.core.annotation.RedisCache;
import com.bbg.core.box.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class RedisCacheAspect {
    @Autowired
    RedisService redisService;

    @Around("@annotation(redisCache)")
    public Object aroundRedisLock(ProceedingJoinPoint point,RedisCache redisCache) throws Throwable {
        Object object= parserSpEL(redisCache.value(),point);
        return point.proceed();
    }

    public Object parserSpEL(String key,ProceedingJoinPoint point){
        MethodSignature methodSignature = (MethodSignature)point.getSignature();
        Expression expression = spelExpressionParser.parseExpression(key);
        EvaluationContext context = new StandardEvaluationContext();
        String[] argNames = methodSignature.getParameterNames();
        Object[] argValues = point.getArgs();
        for (int i = 0; i < argValues.length; i++) {
            context.setVariable(argNames[i],argValues[i]);
        }
        return expression.getValue(context);
    }
    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
}
