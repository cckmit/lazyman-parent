package org.lazyman.starter.rabbitmq.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MqConfirmMessageBO implements Serializable {
    private Long id;

    private Boolean result;

    private String remark;
}
