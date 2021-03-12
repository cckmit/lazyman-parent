package org.lazyman.boot.common.constant;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.lazyman.common.constant.ErrCode;

/**
 * @author wanglong
 */
@AllArgsConstructor
@Getter
public enum LazymanErrCode implements ErrCode {
    //通用
    KAPTCHA_INVALID(101010, "图形验证码已失效"),
    KAPTCHA_ERROR(101011, "图形验证码输入错误"),
    SMS_VERIFYCODE_INVALID(101012, "短信验证码已失效"),
    SMS_VERIFYCODE_ERROR(101013, "短信验证码输入错误"),
    //用户管理
    USER_NOT_FOUND(101110, "用户未找到"),
    USER_EXISTS(101111, "用户已存在"),
    USERNAME_PWD_ERROR(101112, "用户名或密码输入错误"),
    USER_DISABLED(101113, "用户已禁用"),
    OLD_PWD_ERROR(101114, "原密码输入错误"),
    MOBILE_EXISTS(101115, "手机号已被占用"),
    //短信
    SMS_VENDOR_NOT_FOUND(101210, "短信厂商未找到"),
    SMS_TEMPLATE_NOT_FOUND(101211, "短信模板未找到"),
    ;
    private Integer errCode;
    private String message;

    public static LazymanErrCode findByErrCode(Integer errCode) {
        if (ObjectUtil.isNull(errCode)) {
            return null;
        }
        for (LazymanErrCode lazymanErrCode : values()) {
            if (errCode.intValue() == lazymanErrCode.errCode.intValue()) {
                return lazymanErrCode;
            }
        }
        return null;
    }
}
