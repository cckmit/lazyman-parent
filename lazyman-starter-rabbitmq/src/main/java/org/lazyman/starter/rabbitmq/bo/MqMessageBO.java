package org.lazyman.starter.rabbitmq.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MqMessageBO implements Serializable {
    private Long id;

    private Long tenantId;

    private String tag;

    private Long timestamp;

    private String nonce;

    private String body;

    private String sign;
}
