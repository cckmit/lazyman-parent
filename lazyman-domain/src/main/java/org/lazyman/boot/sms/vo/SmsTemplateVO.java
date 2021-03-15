package org.lazyman.boot.sms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.vo.BaseVO;
@Data
@ApiModel(value="短信模板返回对象")
public class SmsTemplateVO extends BaseVO {

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
