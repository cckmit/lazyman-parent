package org.lazyman.boot.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "系统菜单树返回对象")
public class SysMenuTreeVO extends SysMenuVO {
    @ApiModelProperty(value = "叶子节点列表")
    private List<SysMenuTreeVO> children;
}
