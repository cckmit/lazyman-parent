package org.lazyman.boot.common.constant;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author krx
 */
@AllArgsConstructor
@Getter
public enum DataScope {
    ALL(1, "全部"),
    CUSTOM(2, "自定义"),
    OWN_DEPT(3, "本部门"),
    OWN_DEPT_CHILDREN(4, "本部门及以下"),
    USER(5, "仅本人"),
    ;

    private Integer code;
    private String desc;

    public static DataScope findByCode(Integer code) {
        if (ObjectUtil.isEmpty(code)) {
            return null;
        }
        for (DataScope menuType : values()) {
            if (menuType.code.equals(code)) {
                return menuType;
            }
        }
        return null;
    }
}
