package org.lazyman.starter.pay.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.constant.StringPool;
import org.lazyman.common.exception.BizException;
import org.lazyman.starter.pay.config.AlipayApiProperties;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AlipayHelper {
    private AlipayApiProperties alipayApiProperties;

    private String createAlipayUrl(String url, Map<String, String> params) {
        if (params != null && params.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    sb.append(entry.getKey()).append(StringPool.EQUALS).append(URLEncoder.encode(entry.getValue(), StringPool.UTF_8)).append("&");
                } catch (UnsupportedEncodingException e) {
                    throw new BizException(CommonErrCode.INTERNAL_SERVER_ERROR, e);
                }
            }
            String paramString = sb.toString();
            return url + paramString.substring(0, paramString.length() - 1);
        }
        return url;
    }

    /**
     * 支付宝生成订单
     *
     * @param orderNo
     * @param amount
     * @param subject
     * @param body
     * @return
     */
    public String createAlipayOrder(String orderNo, BigDecimal amount, String subject, String body) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("service", "create_direct_pay_by_user");
        params.put("partner", alipayApiProperties.getPartner());
        params.put("seller_id", alipayApiProperties.getPartner());
        params.put("_input_charset", StringPool.UTF_8);
        params.put("payment_type", "1");
        params.put("notify_url", alipayApiProperties.getNotifyUrl());
        params.put("return_url", alipayApiProperties.getReturnUrl());
        params.put("out_trade_no", orderNo);
        params.put("total_fee", Convert.toStr(amount));
        params.put("subject", subject);
        params.put("body", body);
        try {
            String sign = AlipaySignature.rsaSign(params,
                    alipayApiProperties.getPriKey(), StringPool.UTF_8);
            params.put("sign", sign);
            params.put("sign_type", StringPool.RSA);
            return this.createAlipayUrl(alipayApiProperties.getGateway(),
                    params);
        } catch (AlipayApiException e) {
            throw new BizException(CommonErrCode.INTERNAL_SERVER_ERROR, e);
        }
    }

    public void checkSign(Map<String, String> params) {
        boolean result = false;
        try {
            result = AlipaySignature.rsaCheckV1(params,
                    alipayApiProperties.getAlipayPubKey(), StringPool.UTF_8,
                    StringPool.RSA);
        } catch (AlipayApiException e) {
            log.error("支付宝回调验签异常,参数：{}", JSONUtil.toJsonStr(params), e);
            throw new BizException(CommonErrCode.INTERNAL_SERVER_ERROR, e);
        }
        if (!result) {
            log.error("支付宝回调验签失败,参数：{}", JSONUtil.toJsonStr(params));
            throw new BizException(CommonErrCode.INTERNAL_SERVER_ERROR, "支付宝回调验签失败");
        }
    }
}
