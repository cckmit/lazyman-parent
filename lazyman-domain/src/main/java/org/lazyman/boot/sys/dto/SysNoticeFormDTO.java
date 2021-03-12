package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.dto.BaseDTO;
@ApiModel(value="系统通知公告表单参数对象")
@Data
public class SysNoticeFormDTO extends BaseDTO {

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
