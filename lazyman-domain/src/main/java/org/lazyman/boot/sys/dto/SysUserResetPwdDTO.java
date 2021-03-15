package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.dto.BaseDTO;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "系统用户重置密码表单参数对象")
@Data
public class SysUserResetPwdDTO extends BaseDTO {
    @ApiModelProperty(value = "重置密码")
    @NotBlank
    private String password;
}
