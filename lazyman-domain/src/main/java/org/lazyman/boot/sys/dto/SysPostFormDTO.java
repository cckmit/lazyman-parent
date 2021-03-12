package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.dto.BaseDTO;
@ApiModel(value="系统岗位表单参数对象")
@Data
public class SysPostFormDTO extends BaseDTO {

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
