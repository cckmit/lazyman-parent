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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("OSS对象上传返回对象")
public class OssFile implements Serializable {
    @ApiModelProperty(value = "原名称")
    private String originalName;

    @ApiModelProperty(value = "存储名称")
    private String fileName;

    @ApiModelProperty(value = "MIME类型")
    private String contentType;

    @ApiModelProperty(value = "哈希值")
    public String hash;

    @ApiModelProperty(value = "大小")
    private Long length;

    @ApiModelProperty(value = "访问域名")
    private String domain;

    @ApiModelProperty(value = "访问链接")
    private String link;

    @ApiModelProperty(value = "上传时间")
    private Date putTime;
}
