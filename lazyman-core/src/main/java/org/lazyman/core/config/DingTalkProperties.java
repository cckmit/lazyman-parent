package org.lazyman.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "dingtalk.api")
public class DingTalkProperties {
    private String url;
    private Boolean alertSwitch = false;
    private String alertMsgTpl;
}
