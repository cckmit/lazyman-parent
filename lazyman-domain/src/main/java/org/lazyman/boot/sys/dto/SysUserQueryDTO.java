package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.dto.BaseQueryDTO;

@ApiModel(value = "系统用户查询参数对象")
@Data
public class SysUserQueryDTO extends BaseQueryDTO {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;
}
