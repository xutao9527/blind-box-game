package com.bbg.core.base;

import cn.hutool.core.lang.Pair;
import com.bbg.core.annotation.RedisLock;
import com.esotericsoftware.minlog.Log;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class RedisBaseImpl implements RedisBase {
    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;
    @Autowired
    protected RedissonClient redissonClient;

    public Pair<Boolean, RLock> tryLock(RedisLock redisLock,String lockKey) throws InterruptedException {
        RLock rLock = redissonClient.getLock(lockKey);
        boolean lockRet;
        if (redisLock.leaseFlag()) {
            lockRet = rLock.tryLock(redisLock.waitTime(), redisLock.leaseTime(), redisLock.timeUnit());
        } else {
            lockRet = rLock.tryLock(redisLock.waitTime(), redisLock.timeUnit());
        }
        return new Pair<>(lockRet, rLock);
    }

    public void unLock(RLock rLock) {
        if (rLock.isHeldByCurrentThread()) {
            rLock.unlock();
        }
    }

    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void expire(String key, Long liveTime, TimeUnit unit) {
        redisTemplate.expire(key, liveTime, unit);
    }

    public void set(String key, Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    public void set(String key, Object value, Long liveTime, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, liveTime, unit);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                redisTemplate.delete(key);

            } catch (InterruptedException e) {
                Log.error("delete redis key error", e);
            }
        });
    }
}
