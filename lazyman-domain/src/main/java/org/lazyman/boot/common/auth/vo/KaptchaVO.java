package org.lazyman.boot.common.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("图形验证码返回对象")
public class KaptchaVO implements Serializable {
    @ApiModelProperty("base64图形数据")
    private String img;
    @ApiModelProperty("验证码唯一标识")
    private String uuid;
}
