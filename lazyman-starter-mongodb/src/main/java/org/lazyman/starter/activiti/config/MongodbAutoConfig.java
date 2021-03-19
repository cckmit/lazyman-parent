package org.lazyman.starter.activiti.config;

import org.lazyman.starter.activiti.MongodbHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongodbAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public MongodbHelper mongodbHelper(MongoTemplate mongoTemplate) {
        return new MongodbHelper(mongoTemplate);
    }
}
