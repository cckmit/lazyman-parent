package org.lazyman.boot.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(AsyncProperties.class)
public class AsyncAutoConfig {
    @Resource
    private AsyncProperties asyncProperties;

    @Primary
    @Bean("asyncTaskExecutor")
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setMaxPoolSize(asyncProperties.getMaxPoolSize());
        asyncTaskExecutor.setCorePoolSize(asyncProperties.getCorePoolSize());
        asyncTaskExecutor.setQueueCapacity(asyncProperties.getQueueCapacity());
        asyncTaskExecutor.setThreadNamePrefix("async-task-thread-pool-");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }
}
