package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.dto.BaseQueryDTO;

@ApiModel(value = "系统菜单查询参数对象")
@Data
public class SysMenuQueryDTO extends BaseQueryDTO {
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;
}
