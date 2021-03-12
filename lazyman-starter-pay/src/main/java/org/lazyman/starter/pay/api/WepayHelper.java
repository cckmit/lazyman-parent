package org.lazyman.starter.pay.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.constant.StringPool;
import org.lazyman.common.exception.BizException;
import org.lazyman.common.util.JAXBUtils;
import org.lazyman.common.util.MD5Utils;
import org.lazyman.common.util.QrCodeUtils;
import org.lazyman.common.util.UUIDUtils;
import org.lazyman.starter.pay.api.dto.WepayUnifiedOrderDTO;
import org.lazyman.starter.pay.api.vo.WepayUnifiedOrderVO;
import org.lazyman.starter.pay.config.WepayApiProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class WepayHelper {
    private WepayApiProperties wepayApiProperties;
    private RestTemplate restTemplate;

    private String sign(Map<String, String> params, String signKey) {
        Set<String> keySet = params.keySet();
        StringBuilder signContent = new StringBuilder();
        for (String key : keySet) {
            Object value = params.get(key);
            signContent.append(key).append(StringPool.EQUALS).append(ObjectUtil.isEmpty(value) ? StringPool.EMPTY : value).append(StringPool.AMPERSAND);
        }
        signContent.append("key=").append(signKey);
        return MD5Utils.encode(signContent.toString()).toUpperCase();
    }

    public String createWepayOrder(String orderNo, BigDecimal amount, String attach, String body, int qrWidth, int qrHeight, String ip) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", wepayApiProperties.getAppId());
        params.put("attach", attach);
        params.put("body", body);
        params.put("mch_id", wepayApiProperties.getMchId());
        params.put("nonce_str", UUIDUtils.getShortUUID());
        params.put("notify_url", wepayApiProperties.getNotifyUrl());
        params.put("out_trade_no", orderNo);
        params.put("spbill_create_ip", ip);
        params.put("total_fee", Convert.toStr(amount.multiply(new BigDecimal(100)).intValue()));
        params.put("trade_type", "NATIVE");
        try {
            String reqSign = this.sign(params, wepayApiProperties.getSignKey());
            params.put("sign", reqSign);
            WepayUnifiedOrderVO wepayUnifiedOrderVO = this.unifiedOrder(params);
            return QrCodeUtils.createQRCode(wepayUnifiedOrderVO.getCode_url(), qrWidth, qrHeight, StringPool.PNG);
        } catch (Exception e) {
            throw new BizException(CommonErrCode.INTERNAL_SERVER_ERROR, e);
        }
    }

    private WepayUnifiedOrderVO unifiedOrder(Map<String, String> params) throws BizException {
        WepayUnifiedOrderDTO wepayUnifiedOrderDTO = new WepayUnifiedOrderDTO();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            ReflectUtil.setFieldValue(wepayUnifiedOrderDTO, key, new Object[]{params.get(key)});
        }
        WepayUnifiedOrderVO wepayUnifiedOrderVO = null;
        try {
            String reqBody = JAXBUtils.convert2Xml(wepayUnifiedOrderDTO, false, StringPool.UTF_8);
            log.info("微信支付统一下单开始，请求报文：[{}]", reqBody);
            HttpEntity<String> formEntity = new HttpEntity<>(reqBody);
            ResponseEntity<String> response = restTemplate.postForEntity(wepayApiProperties.getGateway(), formEntity, String.class);
            String body = response.getBody();
            log.info("微信支付统一下单结束，响应报文：[{}]", body);
            wepayUnifiedOrderVO = JAXBUtils.convert2JavaBean(body, WepayUnifiedOrderVO.class);
        } catch (Exception e) {
            log.error("微信支付统一下单异常", e);
            throw new BizException(CommonErrCode.INTERNAL_SERVER_ERROR, e);
        }
        if (!StringPool.SUCCESS.equals(wepayUnifiedOrderVO.getReturn_code())
                || !StringPool.SUCCESS.equals(wepayUnifiedOrderVO.getResult_code())) {
            throw new BizException(CommonErrCode.INTERNAL_SERVER_ERROR, wepayUnifiedOrderVO.getReturn_msg());
        }
        return wepayUnifiedOrderVO;
    }
}
