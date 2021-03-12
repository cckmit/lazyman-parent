package org.lazyman.boot.sms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import org.lazyman.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 短信任务表
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sms_task")
@ApiModel(value="短信任务表实体")
public class SmsTask extends BaseEntity {

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
