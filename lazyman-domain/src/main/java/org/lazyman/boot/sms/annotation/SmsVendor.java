package org.lazyman.boot.sms.annotation;

import java.lang.annotation.*;

/**
 * @author wanglong
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SmsVendor {
    String value() default "";
}
