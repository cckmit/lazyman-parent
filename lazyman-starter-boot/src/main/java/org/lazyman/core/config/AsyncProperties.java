package org.lazyman.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "async")
@Data
public class AsyncProperties {
    private int corePoolSize = 4;
    private int maxPoolSize = 16;
    private int queueCapacity = 2147483647;
}
