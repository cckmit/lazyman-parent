package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.dto.BaseDTO;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ApiModel(value = "系统用户表单参数对象")
@Data
public class SysUserFormDTO extends BaseDTO {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "岗位ID")
    private List<Long> postIds;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "角色列表")
    @NotEmpty
    private List<Long> roleIds;
}
