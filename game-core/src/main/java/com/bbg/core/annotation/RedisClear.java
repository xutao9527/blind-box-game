package com.bbg.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedisClear {
    /**
     * 缓存 key 具体值,支持 spring el表达式
     */
    String value() default "";
    /**
     * 缓存 key 前缀
     */
    String key() default "";
    /**
     * 是否需要租户标识
     */
    boolean tenantFlag() default false;
}
