package org.lazyman.starter.oss.qiniu;

import com.qiniu.util.Auth;
import org.lazyman.starter.oss.IOssRule;
import org.lazyman.starter.oss.OssProperties;
import org.lazyman.starter.oss.OssRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


@Configuration
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnProperty(value = "oss.name", havingValue = "qiniu")
public class QiNiuAutoConfig {
    @Resource
    private OssProperties ossProperties;

    @Bean
    @ConditionalOnMissingBean(IOssRule.class)
    public IOssRule ossRule() {
        return new OssRule(ossProperties.getTenantMode());
    }

    @Bean
    @ConditionalOnMissingBean(Auth.class)
    public Auth auth() {
        return Auth.create(ossProperties.getAccessKey(), ossProperties.getSecretKey());
    }

    @Bean
    @ConditionalOnBean({Auth.class, IOssRule.class})
    @ConditionalOnMissingBean(QiNiuTemplate.class)
    public QiNiuTemplate qiNiuTemplate(IOssRule iOssRule, Auth auth) {
        return new QiNiuTemplate(iOssRule, auth, ossProperties);
    }
}


