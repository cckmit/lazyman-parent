package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.dto.BaseQueryDTO;

@ApiModel(value = "系统角色查询参数对象")
@Data
public class SysRoleQueryDTO extends BaseQueryDTO {

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色排序")
    private Integer sort;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "是否超管")
    private Boolean isAdmin;

    @ApiModelProperty(value = "备注")
    private String remark;
}
