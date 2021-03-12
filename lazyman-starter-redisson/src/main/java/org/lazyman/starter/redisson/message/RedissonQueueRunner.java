package org.lazyman.starter.redisson.message;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.starter.redisson.RedissonTemplate;
import org.lazyman.starter.redisson.annotation.MessageExecutor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.task.AsyncTaskExecutor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class RedissonQueueRunner implements ApplicationRunner {
    private RedissonMessageHelper redissonMessageHelper;
    private RedissonTemplate redissonTemplate;
    private AsyncTaskExecutor asyncTaskExecutor;

    @Override
    public void run(ApplicationArguments args) {
        try {
            log.info("执行RedissonQueueRunner...");
            List<RedissonMessageListener> redissonMessageListenerList = redissonMessageHelper.lookupExecutors();
            if (CollectionUtil.isEmpty(redissonMessageListenerList)) {
                log.warn("未找到RedissonMessageExecutor");
                return;
            }
            redissonMessageListenerList.stream().forEach(p -> {
                asyncTaskExecutor.submit(() -> {
                    log.info("初始化{}消息监听器", p.getClass().getName());
                    while (true) {
                        MessageExecutor messageExecutor = p.getClass().getAnnotation(MessageExecutor.class);
                        Object message = redissonTemplate.take(messageExecutor.queue());
                        if (ObjectUtil.isEmpty(message)) {
                            return;
                        }
                        p.onMessage(message);
                    }
                });
            });
        } catch (Exception e) {
            log.error("执行RedissonQueueRunner失败", e);
        }
    }
}
