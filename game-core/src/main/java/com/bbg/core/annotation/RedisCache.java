package com.bbg.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedisCache {
    /**
     * 缓存 key 具体值,支持 spring el表达式
     */
    String value() default "";
    /**
     * 缓存 key 前缀
     */
    String key() default "";
    /**
     * 缓存时长,不传默认为0,则永久缓存
     */
    long liveTime() default 0;
    /**
     * 时间单位，默认为秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    /**
     * 是否需要租户标识
     */
    boolean tenantFlag() default false;
}
