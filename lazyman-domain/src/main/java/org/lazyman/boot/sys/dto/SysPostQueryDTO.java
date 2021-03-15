package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.dto.BaseQueryDTO;
@ApiModel(value="系统岗位查询参数对象")
@Data
public class SysPostQueryDTO extends BaseQueryDTO  {

    @ApiModelProperty(value = "岗位名称")
    private String postName;

    @ApiModelProperty(value = "岗位编码")
    private String postCode;

    @ApiModelProperty(value = "岗位排序")
    private Integer sort;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "备注")
    private String remark;
}
