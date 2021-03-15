package org.lazyman.boot.dingtalk;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.common.constant.StringPool;
import org.lazyman.boot.config.DingTalkProperties;
import org.lazyman.starter.redisson.RedissonTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class DingTalkHelper {
    private DingTalkProperties dingTalkProperties;
    private RestTemplate restTemplate;
    private RedissonTemplate redissonTemplate;

    public interface RedisQueue {
        String QUEUE_DINGTALK_SEND = "queue:dingtalk:send";
    }

    public void sendAlterMsg(String moduleName, String errCode, String errMsg) {
        if (!dingTalkProperties.getAlertSwitch()) {
            return;
        }
        redissonTemplate.convertAndSend(RedisQueue.QUEUE_DINGTALK_SEND, new AlertMessage(moduleName, errCode, errMsg));
    }

    public void handlerAlterMsg(AlertMessage alertMessage) {
        try {
            String content = String.format(dingTalkProperties.getAlertMsgTpl(), alertMessage.getModuleName(), alertMessage.getErrCode(), alertMessage.getErrMsg());
            //消息内容
            Map<String, Object> contentMap = Maps.newHashMap();
            contentMap.put("content", content);
            //通知人
            Map<String, Object> atMap = Maps.newHashMap();
            //1.是否通知所有人
            atMap.put("isAtAll", true);
            //2.通知具体人的手机号码列表
            atMap.put("atMobiles", "");
            Map<String, Object> requestMap = Maps.newHashMap();
            requestMap.put("msgtype", "text");
            requestMap.put("text", contentMap);
            requestMap.put("at", atMap);
            log.info("DingTalk alter notify request, url is {}, params is {}", dingTalkProperties.getUrl(), JSONUtil.toJsonStr(requestMap));
            String result = restTemplate.postForObject(dingTalkProperties.getUrl(), requestMap, String.class);
            if (StrUtil.isBlank(result)) {
                log.error("DingTalk alter notify request no response");
                return;
            }
            JSONObject jsonObject = JSONUtil.parseObj(result);
            if (ObjectUtil.isNull(jsonObject) || !StringPool.ZERO.equals(StrUtil.toString(jsonObject.get("errcode")))) {
                log.error("DingTalk alter notify request fail, fail info is {}", JSONUtil.toJsonStr(jsonObject));
            }
        } catch (Exception e) {
            log.error("DingTalk alter request error", e);
        }
    }
}
