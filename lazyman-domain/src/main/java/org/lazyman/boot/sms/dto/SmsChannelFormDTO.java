package org.lazyman.boot.sms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.dto.BaseDTO;
@ApiModel(value="短信通道表单参数对象")
@Data
public class SmsChannelFormDTO extends BaseDTO {

    @ApiModelProperty(value = "通道名称")
    private String channelName;

    @ApiModelProperty(value = "厂商代码")
    private String vendorCode;

    @ApiModelProperty(value = "短信类型")
    private Integer smsType;

    @ApiModelProperty(value = "Api地址")
    private String apiUrl;

    @ApiModelProperty(value = "Access key")
    private String accessKey;

    @ApiModelProperty(value = "Secret key")
    private String secretKey;

    @ApiModelProperty(value = "启用标识")
    private Boolean state;

    @ApiModelProperty(value = "优先级")
    private Integer priority;

    @ApiModelProperty(value = "备注")
    private String remark;
}
