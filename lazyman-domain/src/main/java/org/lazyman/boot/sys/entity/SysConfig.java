package org.lazyman.boot.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.lazyman.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统配置
 * </p>
 *
 * @author wanglong
 * @since 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_config")
@ApiModel(value="系统配置实体")
public class SysConfig extends BaseEntity {

    @ApiModelProperty(value = "配置名称")
    private String configName;

    @ApiModelProperty(value = "配置key")
    private String configKey;

    @ApiModelProperty(value = "配置value")
    private String configValue;

    @ApiModelProperty(value = "系统内置")
    private Boolean state;

    @ApiModelProperty(value = "备注")
    private String remark;

}
