package org.lazyman.boot.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "系统用户详情返回对象")
public class SysUserDetailVO implements Serializable {
    @ApiModelProperty(value = "用户基本信息")
    private SysUserVO user;

    @ApiModelProperty(value = "部门信息")
    private SysDeptVO dept;

    @ApiModelProperty(value = "岗位信息列表")
    private List<SysPostVO> posts;

    @ApiModelProperty(value = "角色信息列表")
    private List<SysRoleVO> roles;
}
