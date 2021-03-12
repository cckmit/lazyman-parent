package org.lazyman.common.util;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author krx
 */
@AllArgsConstructor
@Getter
public enum ClientFrom {
    APP("APP", "App端"),
    OPENAPI("OPENAPI", "开放应用端"),
    WEB("WEB", "Web管理端"),
    ;

    private String code;
    private String desc;

    public static ClientFrom findByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        for (ClientFrom menuType : values()) {
            if (code.equalsIgnoreCase(menuType.code)) {
                return menuType;
            }
        }
        return null;
    }
}
