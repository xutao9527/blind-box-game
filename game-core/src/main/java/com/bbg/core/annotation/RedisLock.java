package com.bbg.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisLock {

    /**
     * 分布式 key 具体值,支持 spring el表达式
     */
    String value() default "";
    /**
     * 分布式锁 key 前缀
     */
    String key() default "method";
    /**
     * 等待锁超时时间，默认1000毫秒
     */
    long waitTime() default 1000;

    /**
     * 启动自动解锁时间标记
     */
    boolean leaseFlag() default false;
    /**
     * 自动解锁时间，自动解锁时间一定得大于方法执行时间，否则会导致锁提前释放，默认3000毫秒
     * 对时间没有把握可以使用默认的看门狗会自动续期
     */
    long leaseTime() default 3000;

    /**
     * 时间单位，默认为秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
