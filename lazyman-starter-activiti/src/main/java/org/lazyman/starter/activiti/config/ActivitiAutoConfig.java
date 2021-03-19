package org.lazyman.starter.activiti.config;

import org.lazyman.starter.activiti.ActivitiHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivitiAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public ActivitiHelper activitiHelper() {
        return new ActivitiHelper();
    }
}
