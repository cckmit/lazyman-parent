package org.lazyman.boot.common.constant;


import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wanglong
 */
@AllArgsConstructor
@Getter
public enum SmsState {
    TO_SEND(0, "待发送"),
    SEND_SUCCESS(1, "发送成功"),
    SEND_FAILURE(2, "发送失败"),
    ;
    private Integer code;
    private String desc;

    public static SmsState findByCode(Integer code) {
        if (ObjectUtil.isNull(code)) {
            return null;
        }
        for (SmsState smsState : values()) {
            if (code.intValue() == smsState.code.intValue()) {
                return smsState;
            }
        }
        return null;
    }
}
