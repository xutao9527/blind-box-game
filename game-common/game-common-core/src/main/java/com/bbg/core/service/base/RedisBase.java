package com.bbg.core.service.base;

import cn.hutool.core.lang.Pair;
import com.bbg.core.annotation.RedisLock;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public interface RedisBase {
    Pair<Boolean, RLock> tryLock(RedisLock redisLock) throws InterruptedException;

    Pair<Boolean, RLock> tryLock(String lock, long waitTime, TimeUnit unit) throws InterruptedException;

    void unLock(RLock lock);

    Object get(Object key);

    Boolean expire(String key, Long liveTime, TimeUnit unit);

    void set(String key, Object value, Long liveTime, TimeUnit unit);

    void delete(String key);
}
