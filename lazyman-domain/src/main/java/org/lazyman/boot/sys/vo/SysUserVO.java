package org.lazyman.boot.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.vo.BaseVO;

@Data
@ApiModel(value = "系统用户返回对象")
public class SysUserVO extends BaseVO {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "个性头像")
    private String avatar;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "岗位ID")
    private Long postId;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "是否超管")
    private Boolean isAdmin;

    @ApiModelProperty(value = "备注")
    private String remark;
}
