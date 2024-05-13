package com.bbg.core.aop;

import com.bbg.core.annotation.RedisCache;
import com.bbg.core.annotation.RedisClear;
import com.bbg.core.box.service.RedisService;
import com.bbg.core.constants.CacheKey;
import lombok.extern.slf4j.Slf4j;
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

@Aspect
@Component
@Slf4j
public class RedisCacheAspect extends BaseAspect {
    @Autowired
    RedisService redisService;

    @Around("@annotation(redisCache)")
    public Object aroundRedisCache(ProceedingJoinPoint point, RedisCache redisCache) throws Throwable {
        String keyPrefix = redisCache.key();
        String keyValue = parserSpEL(redisCache.value(), point).toString();
        String cacheKey = CacheKey.build(keyPrefix, keyValue);
        Object cacheObject = redisService.get(cacheKey);
        if (cacheObject == null) {
            cacheObject = point.proceed();
            if (cacheObject != null) {
                if(redisCache.liveTime()==0){
                    redisService.set(cacheKey, cacheObject);
                }else{
                    redisService.set(cacheKey, cacheObject,redisCache.liveTime(),redisCache.timeUnit());
                }
            }
        }
        return cacheObject;
    }

    @Around("@annotation(redisClear)")
    public Object aroundRedisClear(ProceedingJoinPoint point, RedisClear redisClear) throws Throwable {
        String keyPrefix = redisClear.key();
        String keyValue = parserSpEL(redisClear.value(), point).toString();
        String cacheKey = CacheKey.build(keyPrefix, keyValue);
        redisService.delete(cacheKey);
        return point.proceed();
    }

}
