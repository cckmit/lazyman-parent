package org.lazyman.boot.auth.service;

import cn.hutool.json.JSONObject;

public interface WechatAuthService {
    JSONObject getByCode(String code);

    JSONObject getUserInfo(String code);
}
