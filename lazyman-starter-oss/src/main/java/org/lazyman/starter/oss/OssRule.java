/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.lazyman.starter.oss;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import org.lazyman.common.constant.StringPool;
import org.lazyman.common.util.ThreadLocalUtils;
import org.lazyman.common.util.FileUtils;
import org.lazyman.common.util.UUIDUtils;

@AllArgsConstructor
public class OssRule implements IOssRule {
    private final Boolean tenantMode;

    @Override
    public String bucketName(String bucketName) {
        return bucketName;
    }

    @Override
    public String fileName(String originalFilename) {
        String tenantId = (tenantMode) ? Convert.toStr(ThreadLocalUtils.getCurrentTenantId()).concat(StringPool.SLASH) : StringPool.EMPTY;
        String extName = FileUtils.getFileExtension(originalFilename);
        return tenantId.concat(DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_FORMAT))
                .concat(StringPool.SLASH + UUIDUtils.getShortUUID())
                .concat(StrUtil.isNotBlank(extName) ? StringPool.DOT + extName : StringPool.EMPTY);
    }
}
