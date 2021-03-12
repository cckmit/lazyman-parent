package org.lazyman.starter.redisson.annotation;

import java.lang.annotation.*;

/**
 * @author wanglong
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MessageExecutor {
    String queue() default "";
}
