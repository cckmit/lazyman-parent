package org.lazyman.starter.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wepay.api")
@Data
public class WepayApiProperties {
    private String gateway;
    private String appId;
    private String mchId;
    private String signKey;
    private String notifyUrl;
}
