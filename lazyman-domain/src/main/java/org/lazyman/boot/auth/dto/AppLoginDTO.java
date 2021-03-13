package org.lazyman.boot.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.dto.BaseDTO;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "APP用户登录参数对象")
@Data
public class AppLoginDTO extends BaseDTO {
    @ApiModelProperty(value = "手机号码", required = true)
    @NotBlank
    private String mobile;

    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank
    private String verifyCode;

    @ApiModelProperty(value = "微信开放ID", hidden = true)
    private String openId;
}
