package org.lazyman.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tenant")
@Data
public class TenantProperties {
    /**
     * 是否开启租户模式
     */
    private Boolean enable = false;

    /**
     * 需要排除的多租户的表
     */
    private String ignoreTables;

    /**
     * 多租户字段名称
     */
    private String column = "tenant_id";
}
