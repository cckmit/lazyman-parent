package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.dto.BaseDTO;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "用户中心修改密码表单参数对象")
@Data
public class SysUserUpdatePwdDTO extends BaseDTO {
    @ApiModelProperty(value = "原密码")
    @NotBlank
    private String oldPassword;

    @ApiModelProperty(value = "新密码")
    @NotBlank
    private String newPassword;
}
