package org.lazyman.boot.config;

import org.lazyman.boot.dingtalk.DingTalkHelper;
import org.lazyman.boot.dingtalk.listener.DingTalkAlterMessageListener;
import org.lazyman.starter.redisson.RedissonTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(DingTalkProperties.class)
public class DingTalkAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public DingTalkHelper dingTalkHelper(DingTalkProperties dingTalkProperties, RestTemplate restTemplate, RedissonTemplate redissonTemplate) {
        return new DingTalkHelper(dingTalkProperties, restTemplate, redissonTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public DingTalkAlterMessageListener dingTalkAlterMessageExecutor(DingTalkHelper dingTalkHelper) {
        return new DingTalkAlterMessageListener(dingTalkHelper);
    }
}
