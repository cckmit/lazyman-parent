package org.lazyman.starter.redisson.config;

import io.netty.channel.nio.NioEventLoopGroup;
import org.lazyman.starter.redisson.RedissonTemplate;
import org.lazyman.starter.redisson.aop.aspect.DistributedLockAspect;
import org.lazyman.starter.redisson.aop.aspect.IdempotencyAspect;
import org.lazyman.starter.redisson.aop.aspect.RateLimitAspect;
import org.lazyman.starter.redisson.message.RedissonMessageHelper;
import org.lazyman.starter.redisson.message.RedissonQueueRunner;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
@ConditionalOnProperty(name = "redisson.enable", havingValue = "true")
@EnableCaching
public class RedissonAutoConfig extends CachingConfigurerSupport {
    @Resource
    private RedissonProperties redissonProperties;

    @Bean
    @ConditionalOnMissingBean
    public RedissonClient redissonClient() {
        Config config = new Config();
        if (RedissonProperties.REDIS_MODE_STANDALONE.equalsIgnoreCase(redissonProperties.getMode())) {
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig.setAddress(RedissonProperties.PROTOCOL_HEADER + redissonProperties.getServerAddrs().get(0)).setTimeout(redissonProperties.getTimeout()).setKeepAlive(true);
            if (!StringUtils.isEmpty(redissonProperties.getPassword())) {
                singleServerConfig.setPassword(redissonProperties.getPassword());
            }
        } else if (RedissonProperties.REDIS_MODE_CLUSTER.equalsIgnoreCase(redissonProperties.getMode())) {
            List<String> clusterNodes = new ArrayList<>();
            for (String node : redissonProperties.getServerAddrs()) {
                clusterNodes.add(RedissonProperties.PROTOCOL_HEADER + node);
            }
            ClusterServersConfig clusterServersConfig = config.useClusterServers();
            clusterServersConfig.addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()])).setTimeout(redissonProperties.getTimeout()).setKeepAlive(true);
            if (!StringUtils.isEmpty(redissonProperties.getPassword())) {
                clusterServersConfig.setPassword(redissonProperties.getPassword());
            }
        }
        config.setEventLoopGroup(new NioEventLoopGroup());
        Codec codec = new JsonJacksonCodec();
        config.setCodec(codec);
        return Redisson.create(config);
    }

    @Primary
    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<>(0);
        return new RedissonSpringCacheManager(redissonClient, config);
    }

    @Bean
    @ConditionalOnMissingBean
    public RedissonTemplate redissonTemplate(RedissonClient redissonClient) {
        return new RedissonTemplate(redissonClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public DistributedLockAspect distributedLockAspect(RedissonTemplate redissonTemplate) {
        return new DistributedLockAspect(redissonTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public IdempotencyAspect idempotencyAspect(RedissonTemplate redissonTemplate) {
        return new IdempotencyAspect(redissonTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public RateLimitAspect rateLimitAspect(RedissonTemplate redissonTemplate) {
        return new RateLimitAspect(redissonTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public RedissonMessageHelper redissonMessageHelper() {
        return new RedissonMessageHelper();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedissonQueueRunner redissonQueueRunner(RedissonMessageHelper redissonMessageHelper, RedissonTemplate redissonTemplate, AsyncTaskExecutor asyncTaskExecutor) {
        return new RedissonQueueRunner(redissonMessageHelper, redissonTemplate, asyncTaskExecutor);
    }
}
