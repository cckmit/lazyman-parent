package org.lazyman.boot.common.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.sys.vo.SysUserVO;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "登录用户信息返回对象", description = "登录用户信息返回对象")
public class LoginUserInfoVO implements Serializable {
    @ApiModelProperty(value = "用户基本信息")
    private SysUserVO userInfo;

    @ApiModelProperty(value = "角色权限")
    private List<String> roles;

    @ApiModelProperty(value = "功能权限")
    private List<String> permissions;
}
