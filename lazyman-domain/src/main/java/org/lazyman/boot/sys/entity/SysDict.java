package org.lazyman.boot.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.lazyman.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统字典
 * </p>
 *
 * @author wanglong
 * @since 2021-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_dict")
@ApiModel(value="系统字典实体")
public class SysDict extends BaseEntity {

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "备注")
    private String remark;

}
