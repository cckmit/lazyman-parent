package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.dto.BaseDTO;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "系统用户表单参数对象")
@Data
public class SysUserProfileFormDTO extends BaseDTO {
    @ApiModelProperty(value = "昵称")
    @NotBlank
    private String nickname;

    @ApiModelProperty(value = "个性头像")
    private String avatar;

    @ApiModelProperty(value = "手机")
    @NotBlank
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    @NotBlank
    private String email;

    @ApiModelProperty(value = "性别")
    @NotBlank
    private String sex;
}
