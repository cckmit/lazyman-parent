package org.lazyman.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "swagger")
@Data
public class Swagger2Properties {
    private boolean enable = true;
}
