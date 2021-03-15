package org.lazyman.boot.sms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.vo.BaseVO;
@Data
@ApiModel(value="短信通道返回对象")
public class SmsChannelVO extends BaseVO {

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
