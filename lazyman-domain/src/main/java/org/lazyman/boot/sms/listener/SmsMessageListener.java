package org.lazyman.boot.sms.listener;

import lombok.extern.slf4j.Slf4j;
import org.lazyman.boot.common.constant.LazymanConstant;
import org.lazyman.boot.sms.bo.SmsMessageBO;
import org.lazyman.boot.sms.service.ISmsTaskService;
import org.lazyman.starter.redisson.annotation.MessageExecutor;
import org.lazyman.starter.redisson.message.RedissonMessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@MessageExecutor(queue = LazymanConstant.RedisQueue.QUEUE_SMS_SEND)
@Component
@Slf4j
public class SmsMessageListener implements RedissonMessageListener<SmsMessageBO> {
    @Resource
    private ISmsTaskService iSmsTaskService;

    @Override
    public void onMessage(SmsMessageBO smsMessageBO) {
        try {
            iSmsTaskService.handleSmsTask(smsMessageBO);
        } catch (Exception e) {
            log.error("短信发送消息消费失败", e);
        }
    }
}
