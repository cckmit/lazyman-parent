package org.lazyman.boot.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.vo.BaseVO;
@Data
@ApiModel(value="系统配置返回对象")
public class SysConfigVO extends BaseVO {

    @ApiModelProperty(value = "配置名称")
    private String configName;

    @ApiModelProperty(value = "配置key")
    private String configKey;

    @ApiModelProperty(value = "配置value")
    private String configValue;

    @ApiModelProperty(value = "系统内置")
    private Boolean state;

    @ApiModelProperty(value = "备注")
    private String remark;
}
