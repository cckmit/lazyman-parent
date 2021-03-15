package org.lazyman.boot.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lazyman.boot.base.entity.BaseEntity;

/**
 * <p>
 * 系统角色
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_role")
@ApiModel(value = "系统角色实体")
public class SysRole extends BaseEntity {

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色排序")
    private Integer sort;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "数据权限范围")
    private Integer dataScope;

    @ApiModelProperty(value = "是否超管")
    private Boolean isAdmin;

    @ApiModelProperty(value = "备注")
    private String remark;

}
