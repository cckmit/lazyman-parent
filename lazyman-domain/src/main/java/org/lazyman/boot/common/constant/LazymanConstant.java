package org.lazyman.boot.common.constant;

public interface LazymanConstant {
    Long MENU_ROOT_ID = 0L;
    int KAPTCHA_VERIFYCODE_EXPIRE_SECONDS = 60;
    String COUNTRY_CODE = "86";
    int SMS_VERIFYCODE_EXPIRE_SECONDS = 5 * 60;

    /**
     * 系统权限
     */
    interface Permission {
        String ADMIN = "*:*:*";
        /**
         * 用户
         **/
        String SYSTEM_USER_ADD = "system:user:add";
        String SYSTEM_USER_EDIT = "system:user:edit";
        String SYSTEM_USER_REMOVE = "system:user:remove";
        String SYSTEM_USER_RESET_PWD = "system:user:resetPwd";
        /**
         * 角色
         **/
        String SYSTEM_ROLE_ADD = "system:role:add";
        String SYSTEM_ROLE_EDIT = "system:role:edit";
        String SYSTEM_ROLE_REMOVE = "system:role:remove";
        /**
         * 菜单
         **/
        String SYSTEM_MENU_ADD = "system:menu:add";
        String SYSTEM_MENU_EDIT = "system:menu:edit";
        String SYSTEM_MENU_REMOVE = "system:menu:remove";
    }

    /**
     * redis缓存key
     */
    interface RedisKey {
        String AUTH_USER_PREFIX = "auth:user:";
        String AUTH_KAPTCHA_PREFIX = "auth:kaptcha:";
        String AUTH_SMS_VERIFYCODE_PREFIX = "auth:sms:verifyCode:";
    }

    /**
     * redis队列
     */
    interface RedisQueue {
        String QUEUE_SMS_SEND = "queue:sms:send";
    }

    /**
     * redis锁
     */
    interface RedisLock {
        String APP_USER_PREFIX = "";
    }

    /**
     * 短信通道厂商
     */
    interface SmsChannelVendor {
        String CHUANGLAN = "chuanglan";
        String NXCLOUD = "nxcloud";
        String XINYICHEN = "xinyichen";
    }

    /**
     * 短信模板业务分类
     */
    interface SmsTemplateCategory {
        int SMS_VERIFY_CODE_TEMPLATE = 10;
    }
}
