package org.lazyman.starter.oss.amazon;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.SneakyThrows;
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
@ConditionalOnProperty(value = "oss.name", havingValue = "amazon")
public class AmazonAutoConfig {
    @Resource
    private OssProperties ossProperties;

    @Bean
    @ConditionalOnMissingBean(IOssRule.class)
    public IOssRule ossRule() {
        return new OssRule(ossProperties.getTenantMode());
    }

    @Bean
    @SneakyThrows
    @ConditionalOnMissingBean(AmazonS3.class)
    public AmazonS3 amazonS3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(ossProperties.getAccessKey(), ossProperties.getSecretKey());
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(Regions.fromName(ossProperties.getRegion()))
                .build();
        return amazonS3;
    }

    @Bean
    @ConditionalOnBean({AmazonS3.class, IOssRule.class})
    @ConditionalOnMissingBean(AmazonTemplate.class)
    public AmazonTemplate amazonTemplate(IOssRule ossRule, AmazonS3 amazonS3) {
        return new AmazonTemplate(ossRule, amazonS3, ossProperties);
    }
}


