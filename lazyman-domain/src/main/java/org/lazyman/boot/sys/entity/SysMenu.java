package org.lazyman.boot.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lazyman.core.base.entity.BaseEntity;

/**
 * <p>
 * 系统菜单
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_menu")
@ApiModel(value = "系统菜单实体")
public class SysMenu extends BaseEntity {
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
