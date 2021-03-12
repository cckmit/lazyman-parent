package org.lazyman.starter.rabbitmq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "rabbitmq")
@Data
public class RabbitmqProperties {
    private String secretKey;
}
