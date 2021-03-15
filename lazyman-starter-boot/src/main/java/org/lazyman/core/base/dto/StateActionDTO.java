package org.lazyman.core.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("布尔操作父参数对象")
@Data
public class StateActionDTO extends BaseDTO {
    @ApiModelProperty(value = "启用：true， 禁用: false")
    @NotNull
    private Boolean state;
}
