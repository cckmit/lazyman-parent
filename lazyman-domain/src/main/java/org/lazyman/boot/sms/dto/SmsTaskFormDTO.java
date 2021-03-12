package org.lazyman.boot.sms.dto;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.dto.BaseDTO;
import org.lazyman.core.validator.DateValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "短信任务表表单参数对象")
@Data
public class SmsTaskFormDTO extends BaseDTO {
    @ApiModelProperty(value = "手机号，批量发送用英文逗号隔开", required = true)
    @NotBlank
    private String mobiles;

    @ApiModelProperty(value = "短信模板分类", required = true)
    @NotNull
    private Integer templateCategory;

    @ApiModelProperty("短信模板变量")
    private JSONObject templateParams;

    @ApiModelProperty("发送时间")
    @DateValue(format = {"yyyy-MM-dd HH:mm:ss"})
    private String sendTime;
}
