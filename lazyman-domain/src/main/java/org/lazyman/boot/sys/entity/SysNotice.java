package org.lazyman.boot.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lazyman.boot.base.entity.BaseEntity;

/**
 * <p>
 * 系统通知公告
 * </p>
 *
 * @author wanglong
 * @since 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_notice")
@ApiModel(value = "系统通知公告实体")
public class SysNotice extends BaseEntity {

    @ApiModelProperty(value = "通知标题")
    private String noticeTitle;

    @ApiModelProperty(value = "通知内容")
    private String noticeContent;

    @ApiModelProperty(value = "通知类型")
    private Integer noticeType;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "备注")
    private String remark;

}
