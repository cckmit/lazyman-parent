package org.lazyman.core.base.dto;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@ApiModel("查询父参数对象")
public class BaseQueryDTO extends BaseDTO {
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_SIZE = 10;

    @ApiModelProperty(value = "查询第几页,默认从1开始")
    private Integer pageNo;
    @ApiModelProperty(value = "每页记录数，默认10条")
    private Integer pageSize;
    @ApiModelProperty(value = "开始日期")
    private String beginTime;
    @ApiModelProperty(value = "结束日期")
    private String endTime;

    public Integer getPageNo() {
        if (pageNo == null || pageNo <= 0) {
            return DEFAULT_PAGE;
        }
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize <= 0) {
            return DEFAULT_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @ApiParam(hidden = true)
    public int getOffset() {
        return (this.getPageNo() - 1) * this.getPageSize();
    }

    @ApiParam(hidden = true)
    public int getLimit() {
        return this.getPageSize();
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        if (StrUtil.isBlank(beginTime)) {
            //this.startTime = DateUtil.format(DateUtil.beginOfDay(DateUtil.date()), DatePattern.NORM_DATETIME_FORMAT);
        } else {
            this.beginTime = DateUtil.format(DateUtil.beginOfDay(DateUtil.parseDate(beginTime).toJdkDate()), DatePattern.NORM_DATETIME_FORMAT);
        }
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        if (StrUtil.isBlank(endTime)) {
            //this.endTime = DateUtil.format(DateUtil.endOfDay(DateUtil.date()), DatePattern.NORM_DATETIME_FORMAT);
        } else {
            this.endTime = DateUtil.format(DateUtil.endOfDay(DateUtil.parseDate(endTime).toJdkDate()), DatePattern.NORM_DATETIME_FORMAT);
        }
    }
}
