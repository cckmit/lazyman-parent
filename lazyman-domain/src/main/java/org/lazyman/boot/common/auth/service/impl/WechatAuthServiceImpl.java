package org.lazyman.boot.common.auth.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.boot.common.auth.service.WechatAuthService;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.lazyman.boot.config.WechatAuthProperties;
import org.lazyman.boot.dingtalk.DingTalkHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WechatAuthServiceImpl implements WechatAuthService {
    private static final int SUCCESS = 0;

    @Resource
    private WechatAuthProperties wechatAuthProperties;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DingTalkHelper dingTalkHelper;

    @Override
    public JSONObject getByCode(String code) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("grant_type", "authorization_code");
            params.put("appid", wechatAuthProperties.getAppId());
            params.put("secret", wechatAuthProperties.getSecretKey());
            params.put("code", code);
            log.info("获取微信OAuth信息开始，参数：[{}]", JSONUtil.toJsonStr(params));
            String result = restTemplate.getForObject("https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=:grant_type&appid=:appid&secret=:secret&code=:code", String.class, params);
            log.info("获取微信OAuth信息响应：[{}]", result);
            if (StrUtil.isEmpty(result)) {
                log.error("获取微信OAuth信息失败");
                return null;
            }
            JSONObject jsonObject = JSONUtil.parseObj(result);
            int errcode = jsonObject.getInt("errcode");
            if (errcode != SUCCESS) {
                log.error("获取微信OAuth信息失败，错误码[{}]，错误描述[{}]", jsonObject.getInt("errcode"), jsonObject.getStr("errmsg"));
                return null;
            }
            return jsonObject;
        } catch (Exception e) {
            log.error("获取微信OAuth信息失败", e);
            dingTalkHelper.sendAlterMsg("获取微信OAuth信息", Convert.toStr(CommonErrCode.INTERNAL_SERVER_ERROR.getErrCode()), ExceptionUtil.stacktraceToString(e));
            throw new BizException(CommonErrCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public JSONObject getUserInfo(String code) {
        JSONObject jsonObject = this.getByCode(code);
        try {
            Map<String, String> params = new HashMap<>();
            params.put("access_token", jsonObject.getStr("access_token"));
            params.put("openid", jsonObject.getStr("openid"));
            params.put("lang", "zh_CN");
            log.info("获取微信用户信息开始，参数：[{}]", JSONUtil.toJsonStr(params));
            String result = restTemplate.getForObject("https://api.weixin.qq.com/sns/userinfo?access_token=:access_token&openid=:openid&lang=:lang", String.class, params);
            log.info("获取微信用户信息响应：[{}]", result);
            if (StrUtil.isEmpty(result)) {
                log.error("获取微信用户信息失败");
                return null;
            }
            jsonObject = JSONUtil.parseObj(result);
            int errcode = jsonObject.getInt("errcode");
            if (errcode != SUCCESS) {
                log.error("获取微信用户信息失败，错误码[{}]，错误描述[{}]", jsonObject.getInt("errcode"), jsonObject.getStr("errmsg"));
                return null;
            }
            return jsonObject;
        } catch (Exception e) {
            log.error("获取微信用户信息失败", e);
            dingTalkHelper.sendAlterMsg("获取微信用户信息", Convert.toStr(CommonErrCode.INTERNAL_SERVER_ERROR.getErrCode()), ExceptionUtil.stacktraceToString(e));
            throw new BizException(CommonErrCode.INTERNAL_SERVER_ERROR);
        }
    }
}
