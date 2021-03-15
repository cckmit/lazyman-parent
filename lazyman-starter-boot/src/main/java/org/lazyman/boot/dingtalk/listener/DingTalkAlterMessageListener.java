package org.lazyman.boot.dingtalk.listener;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.boot.dingtalk.AlertMessage;
import org.lazyman.boot.dingtalk.DingTalkHelper;
import org.lazyman.starter.redisson.annotation.MessageExecutor;
import org.lazyman.starter.redisson.message.RedissonMessageListener;

@NoArgsConstructor
@AllArgsConstructor
@MessageExecutor(queue = DingTalkHelper.RedisQueue.QUEUE_DINGTALK_SEND)
@Slf4j
public class DingTalkAlterMessageListener implements RedissonMessageListener<AlertMessage> {
    private DingTalkHelper dingTalkHelper;

    @Override
    public void onMessage(AlertMessage alertMessage) {
        try {
            dingTalkHelper.handlerAlterMsg(alertMessage);
        } catch (Exception e) {
            log.error("消费钉钉预警消息失败", e);
        }
    }
}
