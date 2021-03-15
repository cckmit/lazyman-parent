package org.lazyman.boot.sms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.dto.BaseQueryDTO;

@ApiModel(value = "短信任务表查询参数对象")
@Data
public class SmsTaskQueryDTO extends BaseQueryDTO {
    @ApiModelProperty(value = "模板分类")
    private Integer templateCategory;

    @ApiModelProperty(value = "接收手机号")
    private String mobile;

    @ApiModelProperty(value = "发送状态")
    private Integer state;
}
