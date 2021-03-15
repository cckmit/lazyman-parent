package org.lazyman.boot.handler;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.lazyman.common.util.WebUtils;
import org.lazyman.boot.base.vo.ResultVO;
import org.lazyman.boot.dingtalk.DingTalkHelper;
import org.lazyman.boot.i18n.I18nHelper;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.annotation.Resource;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Resource
    private I18nHelper i18nHelper;
    @Resource
    private DingTalkHelper dingTalkHelper;

    @ExceptionHandler(value = {Throwable.class})
    public ResultVO handleException(Throwable e) {
        int errCode = 200;
        String errMsg = "";
        if (e instanceof HttpRequestMethodNotSupportedException || e instanceof MissingServletRequestParameterException ||
                e instanceof MethodArgumentTypeMismatchException || e instanceof HttpMessageNotReadableException) {
            errCode = CommonErrCode.BAD_REQUEST.getErrCode();
            errMsg = ExceptionUtil.stacktraceToString(e);
            log.warn(errMsg);
        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
            errCode = CommonErrCode.BAD_REQUEST.getErrCode();
            errMsg = bindingResult.getFieldError().getField() + " " + bindingResult.getFieldError().getDefaultMessage();
            log.warn(errMsg);
        } else if (e instanceof MaxUploadSizeExceededException) {
            errCode = CommonErrCode.BAD_REQUEST.getErrCode();
            errMsg = ExceptionUtil.stacktraceToString(e);
            log.warn(errMsg);
        } else if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            errCode = bizException.getErrCode();
            errMsg = i18nHelper.getMessage(Convert.toStr(bizException.getErrCode()));
            errMsg = StrUtil.isEmpty(errMsg) ? bizException.getMessage() : errMsg;
            log.error("Handle global biz exception, request uri is {}, errCode is {}, errMsg is {}", WebUtils.getHttpRequest().getRequestURI(), errCode, errMsg);
        } else {
            errCode = CommonErrCode.INTERNAL_SERVER_ERROR.getErrCode();
            errMsg = ExceptionUtil.stacktraceToString(e);
            log.error("Handle global unknown exception, request uri is {} errCode is {}, errMsg is {}", WebUtils.getHttpRequest().getRequestURI(), errCode, errMsg);
        }
        dingTalkHelper.sendAlterMsg("Global exception handler[" + WebUtils.getHttpRequest().getMethod() + ":" + WebUtils.getHttpRequest().getRequestURI() + "]", Convert.toStr(errCode), errMsg);
        return ResultVO.error(errCode, errMsg);
    }
}