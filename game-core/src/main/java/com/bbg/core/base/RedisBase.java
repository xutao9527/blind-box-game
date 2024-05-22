package com.bbg.core.base;

import cn.hutool.core.lang.Pair;
import com.bbg.core.annotation.RedisLock;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public interface RedisBase {
    Pair<Boolean, RLock> tryLock(RedisLock redisLock,String lockKey) throws InterruptedException;

    void unLock(RLock lock);

    Object get(Object key);

    void expire(String key, Long liveTime, TimeUnit unit);

    void set(String key, Object value);

    void set(String key, Object value, Long liveTime, TimeUnit unit);

    void delete(String key);
}
