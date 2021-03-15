package org.lazyman.boot.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lazyman.boot.base.entity.BaseEntity;

/**
 * <p>
 * 系统字典数据
 * </p>
 *
 * @author wanglong
 * @since 2021-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_dict_data")
@ApiModel(value = "系统字典数据实体")
public class SysDictData extends BaseEntity {

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "字典标签")
    private String dictLabel;

    @ApiModelProperty(value = "数据键值")
    private String dictValue;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "显示排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

}
