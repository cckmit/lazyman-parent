package org.lazyman.boot.common.constant;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author krx
 */
@AllArgsConstructor
@Getter
public enum MenuType {
    DIRECTORY("M", "目录"),
    MENU("C", "菜单"),
    BUTTON("F", "按钮"),
    ;

    private String code;
    private String desc;

    public static MenuType findByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        for (MenuType menuType : values()) {
            if (code.equalsIgnoreCase(menuType.code)) {
                return menuType;
            }
        }
        return null;
    }
}
