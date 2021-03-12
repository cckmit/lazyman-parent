package org.lazyman.boot.sms.handler.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
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
import org.lazyman.core.dingtalk.DingTalkHelper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author wanglong
 */
@Slf4j
@Component
@SmsVendor(LazymanConstant.SmsChannelVendor.NXCLOUD)
public class NxcloudSmsTaskHandler implements SmsTaskHandler {
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
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("appkey", smsChannel.getAccessKey());
            params.add("secretkey", smsChannel.getSecretKey());
            params.add("phone", String.join(StringPool.COMMA, mobiles));
            params.add("content", smsMessageBO.getContent());
            if (ObjectUtil.isNotNull(smsMessageBO.getSendTime())) {
                params.add("task_time", DateUtil.format(smsMessageBO.getSendTime(), DatePattern.NORM_DATETIME_FORMAT));
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
            log.info("Send nxcloud sms reuest, url is {}, params is {}", smsChannel.getApiUrl(), httpEntity);
            JSONObject result = restTemplate.postForObject(smsChannel.getApiUrl(), params, JSONObject.class);
            log.info("Send nxcloud sms response, result is {}", JSONUtil.toJsonStr(result));
            if (ObjectUtil.isNotNull(result) && SUCCESS.equals(result.getStr(CODE))) {
                return true;
            }
            dingTalkHelper.sendAlterMsg("牛信短信", result.getStr(CODE), result.getStr(MESSAGE));
            return false;
        } catch (Exception e) {
            log.info("Send nxcloud sms reuest error", e);
        }
        return false;
    }
}
