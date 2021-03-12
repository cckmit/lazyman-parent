package org.lazyman.starter.rabbitmq;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.lazyman.common.util.IDGeneratorUtils;
import org.lazyman.common.util.MD5Utils;
import org.lazyman.common.util.UUIDUtils;
import org.lazyman.starter.rabbitmq.config.RabbitmqProperties;
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
    private RabbitmqProperties rabbitmqProperties;
    private RabbitTemplate rabbitTemplate;

    public RabbitmqHelper(RabbitmqProperties rabbitmqProperties, RabbitTemplate rabbitTemplate) {
        this.rabbitmqProperties = rabbitmqProperties;
        this.rabbitTemplate = rabbitTemplate;
    }

    public String sign(MqMessage message) {
        String content = JSONUtil.toJsonStr(message.getBody()) + message.getNonce() + message.getTimestamp() + rabbitmqProperties.getSecretKey();
        return MD5Utils.encode(content);
    }

    public void verifySign(MqMessage message) {
        String content = JSONUtil.toJsonStr(message.getBody()) + message.getNonce() + message.getTimestamp() + rabbitmqProperties.getSecretKey();
        String sign = MD5Utils.encode(content);
        if (!sign.equalsIgnoreCase(message.getSign())) {
            throw new BizException(CommonErrCode.FORBIDDEN);
        }
    }

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

    public void sendMessage(MqMessage mqMessage, String exchange, String routingkey) {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.convertAndSend(exchange, routingkey, mqMessage, new CorrelationData(String.valueOf(mqMessage.getId())));
    }

    private void sendConfirmMessage(MqMessage bizMqMessage, boolean result, String remark) {
        //消息结构
        MqMessage mqMessage = new MqMessage();
        mqMessage.setTenantId(bizMqMessage.getTenantId());
        mqMessage.setId(IDGeneratorUtils.getInstance().nextId());
        mqMessage.setTag(RabbitmqConstant.CONSUME_CONFIRM_TAG);
        mqMessage.setNonce(UUIDUtils.getShortUUID());
        mqMessage.setTimestamp(System.currentTimeMillis());
        //消息内容
        MqConfirmMessage mqConfirmMessage = new MqConfirmMessage();
        mqConfirmMessage.setId(bizMqMessage.getId());
        mqConfirmMessage.setResult(result);
        mqConfirmMessage.setRemark(remark);
        mqMessage.setBody(JSONUtil.toJsonStr(mqConfirmMessage));
        String sign = sign(mqMessage);
        mqMessage.setSign(sign);
        sendMessage(mqMessage, RabbitmqConstant.CONSUME_CONFIRM_EXCHANGE, RabbitmqConstant.CONSUME_CONFIRM_ROUTINGKEY);
    }
}
