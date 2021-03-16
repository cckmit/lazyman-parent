package org.lazyman.boot.handler;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.boot.base.vo.ResultVO;
import org.lazyman.boot.i18n.I18nHelper;
import org.lazyman.common.constant.CommonErrCode;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

@RestControllerAdvice
@Slf4j
public class MessageResponseBodyAdvice implements ResponseBodyAdvice {
    @Resource
    private I18nHelper i18nHelper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object result, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (result instanceof ResultVO) {
            ResultVO resultVO = (ResultVO) result;
            if (ObjectUtil.isNotEmpty(resultVO.getCode()) && CommonErrCode.OK.getErrCode().intValue() != resultVO.getCode().intValue()) {
                String message = i18nHelper.getMessage(Convert.toStr(resultVO.getCode()));
                if (StrUtil.isNotBlank(message)) {
                    resultVO.setMessage(message);
                }
            }
        }
        return result;
    }
}
