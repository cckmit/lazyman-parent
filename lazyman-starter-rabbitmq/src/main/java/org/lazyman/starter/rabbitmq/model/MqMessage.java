package org.lazyman.starter.rabbitmq.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MqMessage implements Serializable {
    private Long tenantId;

    private Long id;

    private String tag;

    private Long timestamp;

    private String nonce;

    private String body;

    private String sign;
}
