package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.dto.BaseDTO;

@ApiModel(value = "系统字典表单参数对象")
@Data
public class SysDictFormDTO extends BaseDTO {

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "备注")
    private String remark;
}
