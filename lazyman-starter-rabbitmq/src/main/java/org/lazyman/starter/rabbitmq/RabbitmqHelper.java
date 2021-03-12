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
import org.lazyman.starter.rabbitmq.bo.MqConfirmMessageBO;
import org.lazyman.starter.rabbitmq.bo.MqMessageBO;
import org.lazyman.starter.rabbitmq.config.RabbitmqProperties;
import org.lazyman.starter.rabbitmq.constant.RabbitConstant;
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

    public String sign(MqMessageBO message) {
        String content = JSONUtil.toJsonStr(message.getBody()) + message.getNonce() + message.getTimestamp() + rabbitmqProperties.getSecretKey();
        return MD5Utils.encode(content);
    }

    public void verifySign(MqMessageBO message) {
        String content = JSONUtil.toJsonStr(message.getBody()) + message.getNonce() + message.getTimestamp() + rabbitmqProperties.getSecretKey();
        String sign = MD5Utils.encode(content);
        if (!sign.equalsIgnoreCase(message.getSign())) {
            throw new BizException(CommonErrCode.FORBIDDEN);
        }
    }

    public void confirmSuccess(MqMessageBO mqMessageBO, Channel channel, @Headers Map<String, Object> headers, Throwable throwable) {
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("Confirm success occur exception, delivery tag:{}", tag);
        } finally {
            sendConfirmMessage(mqMessageBO, true, ExceptionUtil.stacktraceToString(throwable));
        }
    }

    public void confirmFail(MqMessageBO mqMessageBO, Channel channel, @Headers Map<String, Object> headers, Throwable throwable) {
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            channel.basicReject(tag, false);
        } catch (Exception e) {
            log.error("Confirm fail occur exception, delivery tag:{}", tag);
        } finally {
            sendConfirmMessage(mqMessageBO, false, ExceptionUtil.stacktraceToString(throwable));
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

    public void sendMessage(MqMessageBO mqMessageBO, String exchange, String routingkey) {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.convertAndSend(exchange, routingkey, mqMessageBO, new CorrelationData(String.valueOf(mqMessageBO.getId())));
    }

    private void sendConfirmMessage(MqMessageBO bizMqMessageBO, boolean result, String remark) {
        //消息结构
        MqMessageBO mqMessageBO = new MqMessageBO();
        mqMessageBO.setTenantId(bizMqMessageBO.getTenantId());
        mqMessageBO.setId(IDGeneratorUtils.getInstance().nextId());
        mqMessageBO.setTag(RabbitConstant.CONSUME_CONFIRM_TAG);
        mqMessageBO.setNonce(UUIDUtils.getShortUUID());
        mqMessageBO.setTimestamp(System.currentTimeMillis());
        //消息内容
        MqConfirmMessageBO mqConfirmMessageBO = new MqConfirmMessageBO();
        mqConfirmMessageBO.setId(bizMqMessageBO.getId());
        mqConfirmMessageBO.setResult(result);
        mqConfirmMessageBO.setRemark(remark);
        mqMessageBO.setBody(JSONUtil.toJsonStr(mqConfirmMessageBO));
        String sign = sign(mqMessageBO);
        mqMessageBO.setSign(sign);
        sendMessage(mqMessageBO, RabbitConstant.CONSUME_CONFIRM_EXCHANGE, RabbitConstant.CONSUME_CONFIRM_ROUTINGKEY);
    }
}
