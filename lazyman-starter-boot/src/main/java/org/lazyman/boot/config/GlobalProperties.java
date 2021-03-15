package org.lazyman.boot.config;

import org.lazyman.common.constant.CommonConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "global")
@Data
public class GlobalProperties {
    private boolean validFailFast = true;
    private String timeZone = CommonConstant.DEFAULT_TIME_ZONE;
}
