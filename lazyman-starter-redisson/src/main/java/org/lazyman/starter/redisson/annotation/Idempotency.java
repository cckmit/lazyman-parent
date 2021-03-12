package org.lazyman.starter.redisson.annotation;

import org.lazyman.starter.redisson.constant.RedisConstant;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Idempotency {
    int timeWindow() default RedisConstant.IDEMPOTENCY_TIME_WINDOW_SECONDS;
}
