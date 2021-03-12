package org.lazyman.starter.pay.config;

import org.lazyman.starter.pay.api.AlipayHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AlipayApiProperties.class)
public class AlipayAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public AlipayHelper alipayHelper(AlipayApiProperties alipayApiProperties) {
        return new AlipayHelper(alipayApiProperties);
    }
}
