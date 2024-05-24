package com.bbg.core.aop;

import cn.hutool.core.lang.Pair;
import com.bbg.core.annotation.RedisLock;
import com.bbg.core.service.RedisService;
import com.bbg.core.constrans.KeyConst;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class RedisLockAspect extends BaseAspect {
    @Autowired
    RedisService redisService;

    @Around("@annotation(redisLock)")
    public Object aroundRedisLock(ProceedingJoinPoint point, RedisLock redisLock) throws Throwable {
        Object result = null;
        Pair<Boolean, RLock> pair = null;
        try {
            String keyPrefix = redisLock.key();
            String keyValue = parserSpEL(redisLock.value(), point).toString();
            String lockKey = KeyConst.build(keyPrefix, keyValue);
            pair = redisService.tryLock(redisLock, lockKey);
            if (pair.getKey()) {
                result = point.proceed();
            }
        } catch (Throwable throwable) {
            log.error("RedisLockAspect:" + throwable.getMessage());
            throw throwable;
        } finally {
            if (pair != null) {
                redisService.unLock(pair.getValue());
            }
        }
        return result;
    }
}
