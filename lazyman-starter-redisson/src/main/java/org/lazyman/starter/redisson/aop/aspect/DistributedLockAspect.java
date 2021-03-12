package org.lazyman.starter.redisson.aop.aspect;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.constant.StringPool;
import org.lazyman.common.exception.BizException;
import org.lazyman.starter.redisson.RedissonTemplate;
import org.lazyman.starter.redisson.annotation.DistributedLock;
import org.lazyman.starter.redisson.annotation.DistributedLockKey;
import org.lazyman.starter.redisson.constant.RedisConstant;
import org.springframework.core.Ordered;

import java.lang.annotation.Annotation;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@AllArgsConstructor
@Aspect
@Slf4j
public class DistributedLockAspect implements Ordered {
    private RedissonTemplate redissonTemplate;

    @Pointcut("@annotation(distributedLock)")
    public void pointCut(DistributedLock distributedLock) {
    }

    @Around("pointCut(distributedLock)")
    public Object around(ProceedingJoinPoint pjp, DistributedLock distributedLock) throws Throwable {
        String lockName = RedisConstant.LOCK_PREFIX + distributedLock.value();
        String lockKey = this.getLockKey(pjp);
        lockKey = StrUtil.isEmpty(lockKey) ? lockName : lockName + StringPool.COLON + lockKey;
        boolean isSuccess = redissonTemplate.tryLock(lockKey, distributedLock.waitTime(), distributedLock.maxLifeTime(), TimeUnit.SECONDS);
        if (isSuccess) {
            try {
                return pjp.proceed();
            } finally {
                redissonTemplate.unlock(lockKey);
            }
        } else {
            throw new BizException(CommonErrCode.REQUEST_TIMEOUT);
        }
    }

    private String getLockKey(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        if (args == null || args.length == 0) {
            return null;
        }
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return null;
        }
        SortedMap<Integer, String> keys = new TreeMap<>();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            DistributedLockKey distributedLockKey = getAnnotation(DistributedLockKey.class, parameterAnnotations[i]);
            if (distributedLockKey == null || args[i] == null) {
                continue;
            }
            String[] fields = distributedLockKey.fields();
            if (fields != null && fields.length > 0) {
                StringBuilder fieldValues = new StringBuilder();
                for (String field : fields) {
                    Object value = BeanUtil.getFieldValue(args[i], field);
                    if (ObjectUtil.isEmpty(value)) {
                        continue;
                    }
                    fieldValues.append(value.toString());
                }
                keys.put(distributedLockKey.order(), fieldValues.toString());
            } else {
                keys.put(distributedLockKey.order(), args[i].toString());
            }
        }
        StringBuilder lockKey = new StringBuilder();
        if (keys != null && keys.size() > 0) {
            for (String value : keys.values()) {
                lockKey.append(value);
            }
        }
        return lockKey.toString();
    }

    private <T extends Annotation> T getAnnotation(final Class<T> annotationClass, final Annotation[] annotations) {
        if (annotations == null || annotations.length == 0) {
            return null;
        }
        for (final Annotation annotation : annotations) {
            if (annotationClass.equals(annotation.annotationType())) {
                return (T) annotation;
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
