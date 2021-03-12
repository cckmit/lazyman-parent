package org.lazyman.starter.rabbitmq.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MqConfirmMessage implements Serializable {
    private Long id;

    private Boolean result;

    private String remark;
}
