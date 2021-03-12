package org.lazyman.starter.xxljob.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(XxlJobProperties.class)
@ConditionalOnProperty(name = "xxl.job.enable", havingValue = "true")
public class XxlJobAutoConfig {
    @Resource
    private XxlJobProperties xxlJobProperties;

    @Bean
    @ConditionalOnMissingBean
    public XxlJobSpringExecutor xxlJobExecutor() {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdminAddresses());
        xxlJobSpringExecutor.setAppname(xxlJobProperties.getExecutorAppname());
        xxlJobSpringExecutor.setAddress(xxlJobProperties.getExecutorAddress());
        xxlJobSpringExecutor.setIp(xxlJobProperties.getExecutorIp());
        xxlJobSpringExecutor.setPort(xxlJobProperties.getExecutorPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(xxlJobProperties.getExecutorLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProperties.getExecutorLogRetentionDays());
        return xxlJobSpringExecutor;
    }
}