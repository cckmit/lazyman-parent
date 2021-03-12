package org.lazyman.boot.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "登录返回对象", description = "登录返回对象")
public class LoginVO implements Serializable {
    @ApiModelProperty(value = "Token令牌")
    private String token;

    @ApiModelProperty(value = "有效时间（秒）")
    private Integer expireIn;

    @ApiModelProperty(value = "是否需要绑定手机")
    private Boolean isBindMobile;
}
