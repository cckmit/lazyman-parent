package org.lazyman.boot.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.lazyman.boot.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统岗位
 * </p>
 *
 * @author wanglong
 * @since 2021-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_post")
@ApiModel(value="系统岗位实体")
public class SysPost extends BaseEntity {

    @ApiModelProperty(value = "岗位名称")
    private String postName;

    @ApiModelProperty(value = "岗位编码")
    private String postCode;

    @ApiModelProperty(value = "岗位排序")
    private Integer sort;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "备注")
    private String remark;

}
