package org.lazyman.boot.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.lazyman.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_role_menu")
@ApiModel(value="实体")
public class SysRoleMenu extends BaseEntity {

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

}
