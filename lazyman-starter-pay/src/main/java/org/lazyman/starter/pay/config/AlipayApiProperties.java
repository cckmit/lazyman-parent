package org.lazyman.starter.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "alipay.api")
@Data
public class AlipayApiProperties {
    private String alipayPubKey;
    private String gateway;
    private String notifyUrl;
    private String partner;
    private String priKey;
    private String returnUrl;
}
