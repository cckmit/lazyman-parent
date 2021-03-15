package org.lazyman.boot.sms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.lazyman.boot.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 短信模板
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sms_template")
@ApiModel(value="短信模板实体")
public class SmsTemplate extends BaseEntity {

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
