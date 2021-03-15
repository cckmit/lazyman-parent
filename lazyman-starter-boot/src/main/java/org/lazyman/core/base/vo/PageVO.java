package org.lazyman.core.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ApiModel("分页查询返回对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> {
    @ApiModelProperty(value = "总记录数")
    protected Long total = 0L;
    @ApiModelProperty(value = "分页列表")
    protected List<T> records = new ArrayList<>(0);
}
