package org.lazyman.starter.redisson.aop.aspect;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.constant.StringPool;
import org.lazyman.common.exception.BizException;
import org.lazyman.common.util.ThreadLocalUtils;
import org.lazyman.common.util.MD5Utils;
import org.lazyman.common.util.WebUtils;
import org.lazyman.starter.redisson.RedissonTemplate;
import org.lazyman.starter.redisson.annotation.Idempotency;
import org.lazyman.starter.redisson.constant.RedisConstant;
import org.springframework.core.Ordered;

import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@AllArgsConstructor
@Aspect
@Slf4j
public class IdempotencyAspect implements Ordered {
    private RedissonTemplate redissonTemplate;

    @Pointcut("@annotation(idempotency)")
    public void pointCut(Idempotency idempotency) {
    }

    @Around("pointCut(idempotency)")
    public Object around(ProceedingJoinPoint pjp, Idempotency idempotency) throws Throwable {
        Long userId = ThreadLocalUtils.getCurrentUserId();
        if (ObjectUtil.isEmpty(userId)) {
            return pjp.proceed();
        }
        StringBuilder requestParam = new StringBuilder();
        requestParam.append(WebUtils.getHttpRequest().getMethod()).append(StringPool.COLON);
        requestParam.append(WebUtils.getHttpRequest().getRequestURI());
        Object[] args = pjp.getArgs();
        if (args != null && args.length > 0) {
            requestParam.append(StringPool.COLON).append(JSONUtil.toJsonStr(args));
        }
        String originalRequestParam = requestParam.toString();
        String digest = MD5Utils.encode(originalRequestParam);
        String key = String.format(RedisConstant.IDEMPOTENCY_KEY_FORMAT, userId, digest);
        //非首次写入即重复提交
        boolean result = redissonTemplate.trySet(key, digest, idempotency.timeWindow(), TimeUnit.SECONDS);
        if (!result) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        return pjp.proceed();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
