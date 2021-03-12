package org.lazyman.starter.redisson.annotation;

import org.lazyman.starter.redisson.constant.RedisConstant;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RequireRateLimit {
    int timeWindow() default RedisConstant.RATELIMITER_TIME_WINDOW_SECONDS;

    int count() default RedisConstant.RATELIMITER_COUNT;
}
