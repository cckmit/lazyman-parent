package org.lazyman.starter.redisson.aop.aspect;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.lazyman.common.util.ThreadLocalUtils;
import org.lazyman.common.util.MD5Utils;
import org.lazyman.common.util.WebUtils;
import org.lazyman.starter.redisson.RedissonTemplate;
import org.lazyman.starter.redisson.annotation.RequireRateLimit;
import org.lazyman.starter.redisson.constant.RedisConstant;
import org.redisson.api.RateIntervalUnit;
import org.springframework.core.Ordered;

@NoArgsConstructor
@AllArgsConstructor
@Aspect
@Slf4j
public class RateLimitAspect implements Ordered {
    private RedissonTemplate redissonTemplate;

    @Pointcut("@annotation(requireRateLimit)")
    public void pointCut(RequireRateLimit requireRateLimit) {
    }

    @Before("pointCut(requireRateLimit)")
    public void before(RequireRateLimit requireRateLimit) {
        String mainKey = null;
        //默认以登录用户为主维度
        Long userId = ThreadLocalUtils.getCurrentUserId();
        if (ObjectUtil.isNotEmpty(userId)) {
            mainKey = Convert.toStr(userId);
        }
        if (StrUtil.isEmpty(mainKey)) {
            //未登录以客户端IP为主维度
            mainKey = WebUtils.getRemoteIpAddr();
        }
        //接口地址为次维度
        String subKey = WebUtils.getHttpRequest().getMethod() + WebUtils.getHttpRequest().getRequestURI();
        String key = RedisConstant.RATELIMITER_PREFIX + MD5Utils.encode(mainKey + subKey);
        boolean isAcquire = redissonTemplate.tryRateLimiterAcquire(key, requireRateLimit.count(), requireRateLimit.timeWindow(), RateIntervalUnit.SECONDS);
        if (!isAcquire) {
            throw new BizException(CommonErrCode.REQUEST_FREQUENTLY);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
