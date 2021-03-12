package org.lazyman.boot.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.dto.BaseDTO;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "系统登录参数对象", description = "系统登录参数对象")
@Data
public class WebLoginDTO extends BaseDTO {
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank
    private String password;

    @ApiModelProperty(value = "nonce", required = true)
    @NotBlank
    private String uuid;

    @ApiModelProperty(value = "kaptcha验证码", required = true)
    @NotBlank
    private String code;
}
