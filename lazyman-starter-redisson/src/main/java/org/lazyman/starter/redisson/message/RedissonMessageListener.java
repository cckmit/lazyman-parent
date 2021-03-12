package org.lazyman.starter.redisson.message;

/**
 * 消息执行接口
 *
 * @param <T>
 */
public interface RedissonMessageListener<T> {
    void onMessage(T t);
}
