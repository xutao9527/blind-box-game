package com.bbg.core.aop;

import cn.hutool.core.lang.Pair;
import com.bbg.core.annotation.RedisLock;
import com.bbg.core.service.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RedisLockAspect {
    @Autowired
    RedisService redisService;

    @Around("@annotation(redisLock)")
    public Object aroundRedisLock(ProceedingJoinPoint point, RedisLock redisLock) throws Throwable {
        Object result = null;
        Pair<Boolean, RLock> pair = null;
        try {
            pair = redisService.tryLock(redisLock);
            if (pair.getKey()) {
                result = point.proceed();
            }
        } catch (Throwable throwable) {
            System.out.println("RedisLockAspect:" + throwable.getMessage());
            throw throwable;
        } finally {
            if (pair != null) {
                redisService.unLock(pair.getValue());
            }
        }
        return result;
    }


}
