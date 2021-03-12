package org.lazyman.boot.sms.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import org.lazyman.boot.common.constant.LazymanErrCode;
import org.lazyman.boot.sms.annotation.SmsVendor;
import org.lazyman.common.exception.BizException;
import org.lazyman.common.util.SpringContextUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author wanglong
 */
@Component
public final class SmsTaskHelper {
    private volatile Map<String, SmsTaskHandler> smsTaskHandlerMap = new HashMap<>();

    public SmsTaskHandler lookupHandler(String chanelCode) {
        if (CollectionUtils.isEmpty(smsTaskHandlerMap)) {
            synchronized (SmsTaskHelper.class) {
                if (CollectionUtils.isEmpty(smsTaskHandlerMap)) {
                    Map<String, Object> map = SpringContextUtils.getBeansWithAnnotation(SmsVendor.class);
                    Set<Map.Entry<String, Object>> entrySet = map.entrySet();
                    entrySet.forEach(p -> {
                        if (p.getValue() instanceof SmsTaskHandler) {
                            SmsTaskHandler smsTaskHandler = (SmsTaskHandler) p.getValue();
                            SmsVendor smsVendor = smsTaskHandler.getClass().getAnnotation(SmsVendor.class);
                            smsTaskHandlerMap.put(smsVendor.value(), smsTaskHandler);
                        }
                    });
                }
            }
        }
        if (CollectionUtils.isEmpty(smsTaskHandlerMap) || ObjectUtil.isEmpty(smsTaskHandlerMap.get(chanelCode))) {
            throw new BizException(LazymanErrCode.SMS_VENDOR_NOT_FOUND);
        }
        return smsTaskHandlerMap.get(chanelCode);
    }

    public String getContent(String template, JSONObject params) {
        if (ObjectUtil.isEmpty(params)) {
            return template;
        }
        String content = template;
        Set<String> keys = params.keySet();
        for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
            String key = it.next();
            content = content.replace("${" + key + "}", String.valueOf(params.get(key)));
        }
        return content;
    }
}
