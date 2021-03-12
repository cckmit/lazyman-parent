package org.lazyman.starter.redisson.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

@ConfigurationProperties(prefix = "redisson")
@Data
public class RedissonProperties {
    public static final String PROTOCOL_HEADER = "redis://";
    public static final String REDIS_MODE_STANDALONE = "standalone";
    public static final String REDIS_MODE_CLUSTER = "cluster";

    private boolean enable = false;
    private String mode = REDIS_MODE_STANDALONE;
    private List<String> serverAddrs = Arrays.asList("127.0.0.1:6379");
    private String password;
    private int timeout = 5000;
}
