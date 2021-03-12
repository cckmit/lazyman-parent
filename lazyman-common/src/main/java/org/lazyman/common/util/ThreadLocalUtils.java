package org.lazyman.common.util;

import org.lazyman.common.constant.CommonConstant;

import java.util.HashMap;
import java.util.Map;

public final class ThreadLocalUtils {
    private static ThreadLocal<Map<String, Object>> threadLocal = ThreadLocal.withInitial(() -> {
        Map<String, Object> map = new HashMap<>();
        map.put(CommonConstant.X_TRACE_ID, IDGeneratorUtils.getInstance().nextId());
        return map;
    });

    private ThreadLocalUtils() {
    }

    public static void setCurrentUserId(Long userId) {
        threadLocal.get().put(CommonConstant.X_USER_ID, userId);
    }

    public static Long getCurrentUserId() {
        return (Long) threadLocal.get().get(CommonConstant.X_USER_ID);
    }

    public static void setCurrentTenantId(Long tenantId) {
        threadLocal.get().put(CommonConstant.X_TENANT_ID, tenantId);
    }

    public static Long getCurrentTenantId() {
        return (Long) threadLocal.get().get(CommonConstant.X_TENANT_ID);
    }

    public static void setRequestBody(String requestBody) {
        threadLocal.get().put(CommonConstant.X_REQUEST_BODY, requestBody);
    }

    public static String getRequestBody() {
        return (String) threadLocal.get().get(CommonConstant.X_REQUEST_BODY);
    }

    public static void setTraceId(Long traceId) {
        threadLocal.get().put(CommonConstant.X_TRACE_ID, traceId);
    }

    public static Long getTraceId() {
        return (Long) threadLocal.get().get(CommonConstant.X_TRACE_ID);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
