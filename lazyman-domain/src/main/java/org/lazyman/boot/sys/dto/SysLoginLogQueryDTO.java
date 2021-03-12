package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.dto.BaseQueryDTO;

@ApiModel(value = "系统登录日志查询参数对象")
@Data
public class SysLoginLogQueryDTO extends BaseQueryDTO {
    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "登录IP")
    private String ipAddr;

    @ApiModelProperty(value = "登录状态")
    private Boolean state;
}
