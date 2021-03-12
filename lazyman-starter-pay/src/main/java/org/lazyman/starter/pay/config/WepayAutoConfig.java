package org.lazyman.starter.pay.config;

import org.lazyman.starter.pay.api.WepayHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(WepayApiProperties.class)
public class WepayAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public WepayHelper wepayHelper(WepayApiProperties wepayApiProperties, RestTemplate restTemplate) {
        return new WepayHelper(wepayApiProperties, restTemplate);
    }
}
