package org.lazyman.boot.sms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.dto.BaseDTO;

@ApiModel(value = "短信模板表单参数对象")
@Data
public class SmsTemplateFormDTO extends BaseDTO {

    @ApiModelProperty(value = "短信类型")
    private Integer smsType;

    @ApiModelProperty(value = "模板分类")
    private Integer category;

    @ApiModelProperty(value = "模板内容")
    private String content;

    @ApiModelProperty(value = "启用标识")
    private Boolean state;

    @ApiModelProperty(value = "备注")
    private String remark;
}
