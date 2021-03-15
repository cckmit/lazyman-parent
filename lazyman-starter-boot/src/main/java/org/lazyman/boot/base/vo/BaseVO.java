package org.lazyman.boot.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("父业务返回对象")
public class BaseVO implements Serializable {
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean isDeleted;
}
