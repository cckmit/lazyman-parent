package org.lazyman.boot.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.lazyman.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统角色部门数据权限
 * </p>
 *
 * @author wanglong
 * @since 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_role_dept")
@ApiModel(value="系统角色部门数据权限实体")
public class SysRoleDept extends BaseEntity {

    private Long roleId;

    private Long deptId;

}
