package org.lazyman.boot.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WechatAuthProperties.class)
public class WechatAuthAutoConfig {

}
