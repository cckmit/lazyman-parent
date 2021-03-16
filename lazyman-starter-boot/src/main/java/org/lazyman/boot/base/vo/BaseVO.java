package org.lazyman.boot.base.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("父业务返回对象")
public class BaseVO implements Serializable {
    @ApiModelProperty(value = "主键ID")
    @ExcelIgnore
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @ExcelIgnore
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @ExcelIgnore
    private String createBy;

    @ApiModelProperty(value = "最后更新时间")
    @ExcelIgnore
    private Date updateTime;

    @ApiModelProperty(value = "最后更新人")
    @ExcelIgnore
    private String updateBy;

    @ApiModelProperty(value = "逻辑删除")
    @ExcelIgnore
    private Boolean isDeleted;
}
