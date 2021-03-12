package org.lazyman.boot.common.constant;

public interface LazymanConstant {
    String AMDIN_PERMISSION = "*:*:*";
    String COUNTRY_CODE = "86";
    Long MENU_ROOT_ID = 0L;
    int KAPTCHA_VERIFYCODE_EXPIRE_SECONDS = 60;
    int SMS_VERIFYCODE_EXPIRE_SECONDS = 5 * 60;

    interface RedisKey {
        String AUTH_USER_PREFIX = "auth:user:";
        String AUTH_KAPTCHA_PREFIX = "auth:kaptcha:";
        String AUTH_SMS_VERIFYCODE_PREFIX = "auth:sms:verifyCode:";
    }

    interface RedisQueue {
        String QUEUE_SMS_SEND = "queue:sms:send";
    }

    interface RedisLock {
        String USER_PREFIX = "";
    }

    interface SmsChannelVendor {
        String CHUANGLAN = "chuanglan";
        String NXCLOUD = "nxcloud";
        String XINYICHEN = "xinyichen";
    }

    interface SmsTemplateCategory {
        int SMS_VERIFY_CODE_TEMPLATE = 10;
    }
}
