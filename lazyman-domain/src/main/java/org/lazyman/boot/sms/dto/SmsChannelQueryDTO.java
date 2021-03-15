package org.lazyman.boot.sms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.dto.BaseQueryDTO;

@ApiModel(value = "短信通道查询参数对象")
@Data
public class SmsChannelQueryDTO extends BaseQueryDTO {
    @ApiModelProperty(value = "通道名称")
    private String channelName;

    @ApiModelProperty(value = "厂商代码")
    private String vendorCode;

    @ApiModelProperty(value = "短信类型")
    private Integer smsType;

    @ApiModelProperty(value = "启用标识")
    private Boolean state;
}
