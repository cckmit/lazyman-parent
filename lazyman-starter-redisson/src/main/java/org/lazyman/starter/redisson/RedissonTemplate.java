package org.lazyman.starter.redisson;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.starter.redisson.exception.RedissonMessageException;
import org.redisson.api.*;

import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class RedissonTemplate {
    private RedissonClient redissonClient;

    public <T> T get(String key) {
        RBucket<T> rBucket = redissonClient.getBucket(key);
        return rBucket.get();
    }

    public void set(String key, Object value) {
        redissonClient.getBucket(key).set(value);
    }

    public void set(String key, Object value, long ttl, TimeUnit timeUnit) {
        redissonClient.getBucket(key).set(value, ttl, timeUnit);
    }

    public boolean trySet(String key, Object value) {
        return redissonClient.getBucket(key).trySet(value);
    }

    public boolean trySet(String key, Object value, long ttl, TimeUnit timeUnit) {
        return redissonClient.getBucket(key).trySet(value, ttl, timeUnit);
    }


    public void expire(String key, long ttl, TimeUnit timeUnit) {
        redissonClient.getBucket(key).expire(ttl, timeUnit);
    }

    public void expireAsync(String key, long ttl, TimeUnit timeUnit) {
        redissonClient.getBucket(key).expireAsync(ttl, timeUnit);
    }

    public void delete(String key) {
        redissonClient.getBucket(key).delete();
    }

    public void deleteAsync(String key) {
        redissonClient.getBucket(key).deleteAsync();
    }

    public <T> T mGet(String key, String field) {
        RMap<String, T> rMap = this.redissonClient.getMapCache(key);
        return rMap.get(field);
    }

    public void mPut(String key, String field, Object value) {
        this.redissonClient.getMapCache(key).put(field, value);
    }

    public void mPut(String key, String field, Object value, long ttl, TimeUnit timeUnit) {
        this.redissonClient.getMapCache(key).put(field, value, ttl, timeUnit);
    }

    public void mRemoveField(String key, String field) {
        this.redissonClient.getMapCache(key).remove(field);
    }

    public void mRemove(String key) {
        this.redissonClient.getMapCache(key).delete();
    }

    public boolean tryRateLimiterAcquire(String key, int count, int interval, RateIntervalUnit rateIntervalUnit) {
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter(key);
        rRateLimiter.trySetRate(RateType.OVERALL, count, interval, RateIntervalUnit.SECONDS);
        return rRateLimiter.tryAcquire();
    }

    public void deleteRateLimiter(String key) {
        redissonClient.getRateLimiter(key).delete();
        redissonClient.getRateLimiter("{" + key + "}:value").delete();
    }

    public void removeRateLimiterAsync(String key) {
        redissonClient.getRateLimiter(key).deleteAsync();
        redissonClient.getRateLimiter("{" + key + "}:value").deleteAsync();
    }

    public boolean tryLock(String key, int waitTime, int maxLifeTime, TimeUnit timeUnit) throws InterruptedException {
        RLock rLock = redissonClient.getLock(key);
        return rLock.tryLock(waitTime, maxLifeTime, timeUnit);
    }

    public void unlock(String key) {
        redissonClient.getLock(key).unlock();
    }

    public <T> void convertAndSend(String queueName, T value, long delay, TimeUnit timeUnit) {
        try {
            log.info("添加到队列[{}][{}][{}]", queueName, JSONUtil.toJsonStr(value), delay);
            RBlockingDeque<T> blockingDeque = redissonClient.getBlockingDeque(queueName);
            if (delay > 0) {
                RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
                delayedQueue.offer(value, delay, timeUnit);
            } else {
                blockingDeque.offer(value);
            }
        } catch (Exception e) {
            log.error("添加到队列失败：", e);
            throw new RedissonMessageException("添加到队列失败", e);
        }
    }

    public <T> void convertAndSend(String queueName, T value) {
        this.convertAndSend(queueName, value, 0, null);
    }

    public <T> T take(String queueName) {
        RBlockingDeque<T> blockingDeque = redissonClient.getBlockingDeque(queueName);
        try {
            return blockingDeque.take();
        } catch (InterruptedException e) {
            log.error("取队列消息失败：", e);
            throw new RedissonMessageException("取队列消息失败", e);
        }
    }
}
