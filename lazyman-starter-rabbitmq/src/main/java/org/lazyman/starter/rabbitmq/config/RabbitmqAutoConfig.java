package org.lazyman.starter.rabbitmq.config;

import org.lazyman.starter.rabbitmq.RabbitmqHelper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RabbitmqProperties.class)
public class RabbitmqAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public RabbitmqHelper rabbitmqHelper(RabbitmqProperties rabbitmqProperties, RabbitTemplate rabbitTemplate) {
        return new RabbitmqHelper(rabbitmqProperties, rabbitTemplate);
    }
}
