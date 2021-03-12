package org.lazyman.boot.sms.handler;

import org.lazyman.boot.sms.bo.SmsMessageBO;
import org.lazyman.boot.sms.entity.SmsChannel;

/**
 * @author wanglong
 */
public interface SmsTaskHandler {
    /**
     * 短信发送服务
     *
     * @param smsMessageBO
     * @param smsChannel
     * @return
     */
    boolean send(SmsMessageBO smsMessageBO, SmsChannel smsChannel);
}
