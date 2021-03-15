package org.lazyman.boot.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("父参数对象")
@Data
public class BaseDTO implements Serializable {
    @ApiModelProperty(value = "ID", hidden = true)
    private Long id;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;
}
