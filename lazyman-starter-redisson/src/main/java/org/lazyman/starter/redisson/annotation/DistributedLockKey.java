package org.lazyman.starter.redisson.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DistributedLockKey {
    int order() default 0;

    String[] fields() default {};
}
