package org.lazyman.starter.redisson.message;

import cn.hutool.core.collection.CollectionUtil;
import org.lazyman.common.util.SpringContextUtils;
import org.lazyman.starter.redisson.annotation.MessageExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wanglong
 */
public final class RedissonMessageHelper {

    public List<RedissonMessageListener> lookupExecutors() {
        List<RedissonMessageListener> redissonMessageListenerList = new ArrayList<>();
        Map<String, Object> messageExecutorMap = SpringContextUtils.getBeansWithAnnotation(MessageExecutor.class);
        if (CollectionUtil.isEmpty(messageExecutorMap)) {
            return redissonMessageListenerList;
        }
        messageExecutorMap.forEach((key, value) -> {
            if (value instanceof RedissonMessageListener) {
                redissonMessageListenerList.add((RedissonMessageListener) value);
            }
        });
        return redissonMessageListenerList;
    }
}
