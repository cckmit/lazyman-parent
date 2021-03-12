package org.lazyman.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "restclient")
@Data
public class RestClientProperties {
    private boolean enable = true;
    private int connectTimeout = 10000;
    private int connectionRequestTimeout = 10000;
    private int readTimeout = 20000;
}
