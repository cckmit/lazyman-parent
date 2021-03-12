package org.lazyman.common.util;

import cn.hutool.core.util.StrUtil;
import org.lazyman.common.constant.StringPool;

import java.io.File;

public final class FileUtils {

    public static String getFileExtension(final String fullName) {
        if (StrUtil.isEmpty(fullName)) {
            return StringPool.EMPTY;
        }
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf(StringPool.DOT);
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
