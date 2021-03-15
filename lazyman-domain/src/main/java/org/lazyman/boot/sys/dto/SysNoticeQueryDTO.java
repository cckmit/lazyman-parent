package org.lazyman.boot.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.dto.BaseQueryDTO;

@ApiModel(value = "系统通知公告查询参数对象")
@Data
public class SysNoticeQueryDTO extends BaseQueryDTO {

    @ApiModelProperty(value = "通知标题")
    private String noticeTitle;

    @ApiModelProperty(value = "通知类型")
    private Integer noticeType;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;
}
