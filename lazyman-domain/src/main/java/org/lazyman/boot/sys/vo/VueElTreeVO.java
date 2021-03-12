package org.lazyman.boot.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "vue el菜单树返回对象")
public class VueElTreeVO implements Serializable {
    private Long id;

    private String label;

    @ApiModelProperty(value = "叶子节点列表")
    private List<VueElTreeVO> children;
}
