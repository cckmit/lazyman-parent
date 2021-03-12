package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.dto.BaseDTO;

import java.util.List;

@ApiModel(value = "系统角色表单参数对象")
@Data
public class SysRoleDataScopeFormDTO extends BaseDTO {
    @ApiModelProperty(value = "数据权限范围")
    private Integer dataScope;

    @ApiModelProperty(value = "部门ID集合")
    private List<Long> deptIds;
}
