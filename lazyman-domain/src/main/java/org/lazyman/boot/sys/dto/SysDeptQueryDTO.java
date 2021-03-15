package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.dto.BaseQueryDTO;

import java.util.List;

@ApiModel(value = "系统部门查询参数对象")
@Data
public class SysDeptQueryDTO extends BaseQueryDTO {
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "排除节点ID集合")
    private List<Long> excludeIds;
}
