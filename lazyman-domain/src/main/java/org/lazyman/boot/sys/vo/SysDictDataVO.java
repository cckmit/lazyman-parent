package org.lazyman.boot.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.vo.BaseVO;

@Data
@ApiModel(value = "系统字典数据返回对象")
public class SysDictDataVO extends BaseVO {

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    @ApiModelProperty(value = "字典标签")
    private String dictLabel;

    @ApiModelProperty(value = "数据键值")
    private String dictValue;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "显示排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;
}
