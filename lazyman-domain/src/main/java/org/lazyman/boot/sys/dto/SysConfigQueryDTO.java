package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.dto.BaseQueryDTO;

@ApiModel(value = "系统配置查询参数对象")
@Data
public class SysConfigQueryDTO extends BaseQueryDTO {

    @ApiModelProperty(value = "配置名称")
    private String configName;

    @ApiModelProperty(value = "配置key")
    private String configKey;

    @ApiModelProperty(value = "系统内置")
    private Boolean state;
}
