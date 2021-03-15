package org.lazyman.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt.token")
public class JwtTokenProperties {
    private String secretKey;
    private int webExpireSeconds;
    private int appExpireSeconds;
}
