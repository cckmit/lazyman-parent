package org.lazyman.starter.mongodb.config;

import org.lazyman.starter.mongodb.MongoHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongodbAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public MongoHelper mongoHelper(MongoTemplate mongoTemplate) {
        return new MongoHelper(mongoTemplate);
    }
}
