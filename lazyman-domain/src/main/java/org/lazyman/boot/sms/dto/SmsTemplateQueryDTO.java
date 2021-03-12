package org.lazyman.boot.sms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.dto.BaseQueryDTO;

@ApiModel(value = "短信模板查询参数对象")
@Data
public class SmsTemplateQueryDTO extends BaseQueryDTO {
    @ApiModelProperty(value = "短信类型")
    private Integer smsType;

    @ApiModelProperty(value = "模板分类")
    private Integer category;

    @ApiModelProperty(value = "启用标识")
    private Boolean state;
}
