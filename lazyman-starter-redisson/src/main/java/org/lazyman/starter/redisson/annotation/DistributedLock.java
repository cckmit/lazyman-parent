package org.lazyman.starter.redisson.annotation;

import org.lazyman.starter.redisson.constant.RedisConstant;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DistributedLock {
    String value() default RedisConstant.LOCK_DEFAULT_NAME;

    int waitTime() default RedisConstant.LOCK_WAIT_SECONDS;

    int maxLifeTime() default RedisConstant.LOCK_MAX_LIFE_SECONDS;
}
