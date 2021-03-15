package org.lazyman.boot.i18n;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.lazyman.common.constant.CommonConstant;
import org.lazyman.common.constant.ErrCode;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

@NoArgsConstructor
@AllArgsConstructor
public class I18nHelper {
    private MessageSource messageSource;

    public String getMessage(String key, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(CommonConstant.I18N_MSG_PREFIX + key, args, defaultMessage, locale);
    }

    public String getMessage(String key, Object[] args) {
        return getMessage(key, args, "");
    }

    public String getMessage(String key) {
        return getMessage(key, null);
    }


    public String getMessage(ErrCode errCode, Object[] args, String defaultMessage) {
        return getMessage(String.valueOf(errCode.getErrCode()), args, defaultMessage);
    }

    public String getMessage(ErrCode errCode, Object[] args) {
        return getMessage(errCode, args, errCode.getMessage());
    }

    public String getMessage(ErrCode errCode) {
        return getMessage(errCode, null);
    }
}
