package org.lazyman.boot.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.vo.BaseVO;

@Data
@ApiModel(value = "系统菜单返回对象")
public class SysMenuVO extends BaseVO {

    @ApiModelProperty(value = "菜单类型")
    private String menuType;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单编码")
    private String menuCode;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "菜单排序")
    private Integer sort;

    @ApiModelProperty(value = "是否外链")
    private Boolean isFrame;

    @ApiModelProperty(value = "是否缓存")
    private Boolean isCache;

    @ApiModelProperty(value = "是否显示")
    private Boolean isVisible;

    @ApiModelProperty(value = "父ID")
    private Long parentId;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "备注")
    private String remark;
}
