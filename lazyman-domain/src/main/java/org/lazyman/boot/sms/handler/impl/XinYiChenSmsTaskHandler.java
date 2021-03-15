package org.lazyman.boot.sms.handler.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jodd.util.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.boot.common.constant.LazymanConstant;
import org.lazyman.boot.sms.annotation.SmsVendor;
import org.lazyman.boot.sms.bo.SmsMessageBO;
import org.lazyman.boot.sms.entity.SmsChannel;
import org.lazyman.boot.sms.handler.SmsTaskHandler;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.util.MD5Utils;
import org.lazyman.boot.dingtalk.DingTalkHelper;
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
@SmsVendor(LazymanConstant.SmsChannelVendor.XINYICHEN)
public class XinYiChenSmsTaskHandler implements SmsTaskHandler {
    private static final String SUCCESS = "<Result>0</Result>";

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DingTalkHelper dingTalkHelper;

    @Override
    public boolean send(SmsMessageBO smsMessageBO, SmsChannel smsChannel) {
        try {
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("loginName", smsChannel.getAccessKey());
            params.add("password", MD5Utils.encode(smsChannel.getSecretKey()).toLowerCase());
            params.add("enterpriseID", smsChannel.getRemark());
            params.add("smsId", smsMessageBO.getBatchId());
            params.add("content", smsMessageBO.getContent());
            params.add("mobiles", String.join(StringPool.COMMA, smsMessageBO.getMobiles()));
            if (StrUtil.isNotBlank(smsMessageBO.getSubPort())) {
                params.add("subPort", smsMessageBO.getSubPort());
            }
            log.info("Send XinYiChen sms reuest, url is {}, params is {}", smsChannel.getApiUrl(), JSONUtil.toJsonStr(params));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
            String result = restTemplate.postForObject(smsChannel.getApiUrl() + "/sendSMS.action", requestEntity, String.class);
            log.info("Send XinYiChen sms response, result is {}", JSONUtil.toJsonStr(result));
            if (StrUtil.isNotEmpty(result) && result.contains(SUCCESS)) {
                return true;
            }
            dingTalkHelper.sendAlterMsg("XinYiChen短信", Convert.toStr(CommonErrCode.INTERNAL_SERVER_ERROR.getErrCode()), result);
            return false;
        } catch (Exception e) {
            log.info("Send XinYiChen sms reuest error", e);
        }
        return false;
    }
}
