package com.bbg.core.aop;


import com.bbg.core.annotation.RedisCache;
import com.bbg.core.box.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.ClassPool;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Object aroundRedisLock(ProceedingJoinPoint point, RedisCache redisCache) throws Throwable {
        String keyPrefix = redisCache.key();
        Object keyValue = parserSpEL(redisCache.value(), point);
        String rediskey = keyPrefix+"::"+keyValue;
        Object cacheObject = redisService.get(rediskey);
        if (cacheObject == null) {
            cacheObject = point.proceed();
            if(cacheObject!=null){
                redisService.set(rediskey,cacheObject);
            }
        }
        return cacheObject;
    }

    public Object parserSpEL(String key, ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Expression expression = spelExpressionParser.parseExpression(key);
        EvaluationContext context = new StandardEvaluationContext();
        String[] argNames = methodSignature.getParameterNames();
        Object[] argValues = point.getArgs();
        for (int i = 0; i < argValues.length; i++) {
            context.setVariable(argNames[i], argValues[i]);
        }
        return expression.getValue(context);
    }

    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
}
