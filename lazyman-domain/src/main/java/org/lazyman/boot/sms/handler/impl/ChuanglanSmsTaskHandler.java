package org.lazyman.boot.sms.handler.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jodd.util.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.boot.common.constant.LazymanConstant;
import org.lazyman.boot.sms.annotation.SmsVendor;
import org.lazyman.boot.sms.bo.SmsMessageBO;
import org.lazyman.boot.sms.entity.SmsChannel;
import org.lazyman.boot.sms.handler.SmsTaskHandler;
import org.lazyman.boot.dingtalk.DingTalkHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author wanglong
 */
@Slf4j
@Component
@SmsVendor(LazymanConstant.SmsChannelVendor.CHUANGLAN)
public class ChuanglanSmsTaskHandler implements SmsTaskHandler {
    public static final String SUCCESS = "0";
    public static final String CODE = "code";
    public static final String MESSAGE = "message";

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DingTalkHelper dingTalkHelper;

    @Override
    public boolean send(SmsMessageBO smsMessageBO, SmsChannel smsChannel) {
        try {
            String[] mobiles = Convert.toStrArray(smsMessageBO.getMobiles());
            for (int i = 0; i < mobiles.length; i++) {
                mobiles[i] = StrUtil.concat(true, LazymanConstant.COUNTRY_CODE, mobiles[i]);
            }
            JSONObject params = new JSONObject();
            params.set("account", smsChannel.getAccessKey());
            params.set("password", smsChannel.getSecretKey());
            params.set("msg", smsMessageBO.getContent());
            params.set("mobile", String.join(StringPool.COMMA, mobiles));
            log.info("Send chuanglan 253 sms reuest, url is {}, params is {}", smsChannel.getApiUrl(), JSONUtil.toJsonStr(params));
            JSONObject result = restTemplate.postForObject(smsChannel.getApiUrl(), params, JSONObject.class);
            log.info("Send chuanglan 253 sms response, result is {}", JSONUtil.toJsonStr(result));
            if (ObjectUtil.isNotNull(result) && SUCCESS.equals(result.getStr(CODE))) {
                return true;
            }
            dingTalkHelper.sendAlterMsg("创蓝短信", result.getStr(CODE), result.getStr(MESSAGE));
            return false;
        } catch (Exception e) {
            log.info("Send chuanglan 253 sms reuest error", e);
        }
        return false;
    }
}
