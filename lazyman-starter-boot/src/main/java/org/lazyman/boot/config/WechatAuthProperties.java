package org.lazyman.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "wechat.auth")
public class WechatAuthProperties {
    private String appId;
    private String secretKey;
}
