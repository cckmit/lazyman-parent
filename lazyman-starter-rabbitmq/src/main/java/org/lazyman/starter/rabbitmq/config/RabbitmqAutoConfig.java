package org.lazyman.starter.rabbitmq.config;

import org.lazyman.starter.rabbitmq.RabbitmqHelper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public RabbitmqHelper rabbitmqHelper(RabbitTemplate rabbitTemplate) {
        return new RabbitmqHelper(rabbitTemplate);
    }
}
