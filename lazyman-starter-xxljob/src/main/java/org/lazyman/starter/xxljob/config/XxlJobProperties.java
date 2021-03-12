package org.lazyman.starter.xxljob.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "xxl.job")
@Data
public class XxlJobProperties {
    private boolean enable = false;

    private String adminAddresses;

    private String accessToken;

    private String executorAppname;

    private String executorAddress;

    private String executorIp;

    private int executorPort;

    private String executorLogPath;

    private int executorLogRetentionDays;
}
