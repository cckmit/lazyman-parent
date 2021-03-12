package org.lazyman.starter.redisson.constant;

public interface RedisConstant {
    String LOCK_PREFIX = "lock:";
    String LOCK_DEFAULT_NAME = "global";
    int LOCK_WAIT_SECONDS = 10;
    int LOCK_MAX_LIFE_SECONDS = 20;
    int IDEMPOTENCY_TIME_WINDOW_SECONDS = 1;
    String IDEMPOTENCY_KEY_FORMAT = "idempotency:%s:%s";
    String RATELIMITER_PREFIX = "ratelimiter:";
    int RATELIMITER_TIME_WINDOW_SECONDS = 1;
    int RATELIMITER_COUNT = 10;
}
