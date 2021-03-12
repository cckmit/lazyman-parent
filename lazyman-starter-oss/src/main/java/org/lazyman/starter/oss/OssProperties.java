package org.lazyman.starter.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {
    private String name;

    private Boolean tenantMode = false;

    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String region;
}
