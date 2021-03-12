package org.lazyman.boot.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.vo.BaseVO;
@Data
@ApiModel(value="系统部门返回对象")
public class SysDeptVO extends BaseVO {

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
