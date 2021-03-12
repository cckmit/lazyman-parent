package org.lazyman.boot.common.constant;


import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wanglong
 */
@AllArgsConstructor
@Getter
public enum SmsType {
    VERIFY(1, "验证码"),
    MARKET(2, "营销"),
    ;
    private Integer code;
    private String desc;

    public static SmsType findByCode(Integer code) {
        if (ObjectUtil.isNull(code)) {
            return null;
        }
        for (SmsType smsTypeEnum : values()) {
            if (code.intValue() == smsTypeEnum.code.intValue()) {
                return smsTypeEnum;
            }
        }
        return null;
    }
}
