package org.lazyman.starter.rabbitmq;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.common.util.IDGeneratorUtils;
import org.lazyman.starter.rabbitmq.constant.RabbitmqConstant;
import org.lazyman.starter.rabbitmq.model.MqConfirmMessage;
import org.lazyman.starter.rabbitmq.model.MqMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class RabbitmqHelper {
    private RabbitTemplate rabbitTemplate;

    public RabbitmqHelper(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 确认成功
     *
     * @param mqMessage
     * @param channel
     * @param headers
     * @param throwable
     */
    public void confirmSuccess(MqMessage mqMessage, Channel channel, @Headers Map<String, Object> headers, Throwable throwable) {
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("Confirm success occur exception, delivery tag:{}", tag);
        } finally {
            sendConfirmMessage(mqMessage, true, ExceptionUtil.stacktraceToString(throwable));
        }
    }

    /**
     * 确认失败
     *
     * @param mqMessage
     * @param channel
     * @param headers
     * @param throwable
     */
    public void confirmFail(MqMessage mqMessage, Channel channel, @Headers Map<String, Object> headers, Throwable throwable) {
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            channel.basicReject(tag, false);
        } catch (Exception e) {
            log.error("Confirm fail occur exception, delivery tag:{}", tag);
        } finally {
            sendConfirmMessage(mqMessage, false, ExceptionUtil.stacktraceToString(throwable));
        }
    }

    private RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, isAck, cause) -> {
        if (isAck) {
            log.debug("Producer ack success, id:{}", correlationData.getId());
        } else {
            log.error("Producer ack fail, id:{},cause:{}", correlationData.getId(), cause);
        }
    };

    private RabbitTemplate.ReturnCallback returnCallback = (message, replyCode, replyText, exchange, routingKey) -> {
        log.error("Producer return, message:{},replyCode:{},replyText:{},exchange:{},routingKey:{}", JSONUtil.toJsonStr(message), replyCode, replyText, exchange, routingKey);
    };

    /**
     * @param mqMessage
     * @param exchange
     * @param routingkey
     */
    public void convertAndSend(MqMessage mqMessage, String exchange, String routingkey) {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.convertAndSend(exchange, routingkey, mqMessage, new CorrelationData(String.valueOf(mqMessage.getMsgId())));
    }

    /**
     * 发送确认消息
     *
     * @param bizMqMessage
     * @param result
     * @param remark
     */
    private void sendConfirmMessage(MqMessage bizMqMessage, boolean result, String remark) {
        //消息结构
        MqMessage mqMessage = new MqMessage();
        mqMessage.setTenantId(bizMqMessage.getTenantId());
        mqMessage.setMsgId(IDGeneratorUtils.getInstance().nextId());
        mqMessage.setTimestamp(System.currentTimeMillis());
        //消息内容
        MqConfirmMessage mqConfirmMessage = new MqConfirmMessage();
        mqConfirmMessage.setMsgId(bizMqMessage.getMsgId());
        mqConfirmMessage.setResult(result);
        mqConfirmMessage.setRemark(remark);
        mqMessage.setBody(JSONUtil.toJsonStr(mqConfirmMessage));
        convertAndSend(mqMessage, RabbitmqConstant.CONSUME_CONFIRM_EXCHANGE, RabbitmqConstant.CONSUME_CONFIRM_ROUTINGKEY);
    }
}
