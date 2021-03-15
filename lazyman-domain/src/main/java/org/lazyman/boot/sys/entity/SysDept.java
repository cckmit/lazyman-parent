package org.lazyman.boot.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.lazyman.boot.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统部门
 * </p>
 *
 * @author wanglong
 * @since 2021-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_dept")
@ApiModel(value="系统部门实体")
public class SysDept extends BaseEntity {

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "部门负责人")
    private String leader;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "部门排序")
    private Integer sort;

    @ApiModelProperty(value = "父ID")
    private Long parentId;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "备注")
    private String remark;

}
