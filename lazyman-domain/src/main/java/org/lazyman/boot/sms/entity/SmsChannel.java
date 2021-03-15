package org.lazyman.boot.sms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.lazyman.boot.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 短信通道
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sms_channel")
@ApiModel(value="短信通道实体")
public class SmsChannel extends BaseEntity {

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
