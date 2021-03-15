package org.lazyman.boot.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.vo.BaseVO;

@Data
@ApiModel(value = "系统角色返回对象")
public class SysRoleVO extends BaseVO {

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色排序")
    private Integer sort;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "数据权限范围")
    private Integer dataScope;

    @ApiModelProperty(value = "是否超管")
    private Boolean isAdmin;

    @ApiModelProperty(value = "备注")
    private String remark;
}
