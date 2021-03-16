package org.lazyman.boot.controller;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.common.constant.StringPool;
import org.lazyman.common.exception.BizException;
import org.lazyman.common.util.JAXBUtils;
import org.lazyman.common.util.ThreadLocalUtils;
import org.lazyman.starter.pay.api.AlipayHelper;
import org.lazyman.starter.pay.api.dto.WepayCallbackDTO;
import org.lazyman.starter.pay.api.vo.WepayCallbackVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Api(tags = "spi回调通知")
@RestController
@RequestMapping("/spi")
@Slf4j
public class SpiController {
    @Resource
    private AlipayHelper alipayHelper;

    @ApiOperation(value = "支付宝回调通知")
    @PostMapping(value = "/alipay_notify")
    public String alipayNotify(HttpServletRequest wepayCallbackDTOuest) {
        Map<String, String> params = new HashMap<>();
        Map wepayCallbackDTOuestParams = wepayCallbackDTOuest.getParameterMap();
        log.info("收到支付宝回调通知，参数：[{}]", JSONUtil.toJsonStr(wepayCallbackDTOuestParams));
        for (Iterator iter = wepayCallbackDTOuestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) wepayCallbackDTOuestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + StringPool.COMMA;
            }
            params.put(name, valueStr);
        }
        try {
            this.alipayHelper.checkSign(params);
            return StringPool.SUCCESS.toLowerCase();
        } catch (BizException e) {
            log.error("支付宝回调通知处理失败", e);
            return StringPool.ERROR.toLowerCase();
        }
    }

    @ApiOperation(value = "微信回调通知")
    @PostMapping(value = "/wepay_notify")
    public String wepayNotify(HttpServletRequest wepayCallbackDTOuest) {
        WepayCallbackVO wepayCallbckVO = new WepayCallbackVO();
        WepayCallbackDTO wepayCallbackDTO = null;
        try {
            String wepayCallbackDTOuestBody = ThreadLocalUtils.getRequestBody();
            log.info("收到微信支付回调通知，参数：[{}]", wepayCallbackDTOuestBody);
            wepayCallbackDTO = JAXBUtils.convert2JavaBean(wepayCallbackDTOuestBody, WepayCallbackDTO.class);
        } catch (Exception e) {
            wepayCallbckVO.setReturn_code(StringPool.FAIL);
            wepayCallbckVO.setReturn_msg("请求异常");
            try {
                return JAXBUtils.convert2Xml(wepayCallbckVO, false, StringPool.UTF_8);
            } catch (Exception e1) {
                return StringPool.EMPTY;
            }
        }
        try {
            if (StringPool.SUCCESS.equals(wepayCallbackDTO.getReturn_code())) {
                //todo 处理业务
                wepayCallbckVO.setReturn_code(StringPool.SUCCESS);
                wepayCallbckVO.setReturn_msg("OK");
            } else {
                wepayCallbckVO.setReturn_code(StringPool.FAIL);
                wepayCallbckVO.setReturn_msg("通信异常");
            }
        } catch (BizException e) {
            wepayCallbckVO.setReturn_code(StringPool.FAIL);
            wepayCallbckVO.setReturn_msg("通知处理失败");
            log.error("微信支付回调通知处理失败", e);
        }
        try {
            return JAXBUtils.convert2Xml(wepayCallbckVO, false, StringPool.UTF_8);
        } catch (Exception e) {
            return StringPool.EMPTY;
        }
    }
}
