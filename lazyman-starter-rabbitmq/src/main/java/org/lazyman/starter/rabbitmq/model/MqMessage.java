package org.lazyman.starter.rabbitmq.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MqMessage implements Serializable {
    private Long tenantId;

    private Long msgId;

    private Long timestamp;

    private String body;
}
