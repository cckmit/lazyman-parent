package org.lazyman.boot.sms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.vo.BaseVO;

import java.util.Date;

@Data
@ApiModel(value = "短信任务表返回对象")
public class SmsTaskVO extends BaseVO {
    @ApiModelProperty(value = "批次号")
    private Long batchId;

    @ApiModelProperty(value = "短信类型")
    private Integer smsType;

    @ApiModelProperty(value = "模板分类")
    private Integer templateCategory;

    @ApiModelProperty(value = "接收手机号")
    private String mobile;

    @ApiModelProperty(value = "短信内容")
    private String content;

    @ApiModelProperty(value = "发送状态")
    private Integer state;

    @ApiModelProperty(value = "发送通道ID")
    private Long channelId;

    @ApiModelProperty(value = "发送时间")
    private Date sendTime;
}
