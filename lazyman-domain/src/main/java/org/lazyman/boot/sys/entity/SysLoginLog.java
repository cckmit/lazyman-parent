package org.lazyman.boot.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.lazyman.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统登录日志
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_login_log")
@ApiModel(value="系统登录日志实体")
public class SysLoginLog extends BaseEntity {

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "登录IP")
    private String ipAddr;

    @ApiModelProperty(value = "登录地点")
    private String location;

    @ApiModelProperty(value = "操作系统")
    private String os;

    @ApiModelProperty(value = "浏览器")
    private String browser;

    @ApiModelProperty(value = "登录状态")
    private Boolean state;

    @ApiModelProperty(value = "登录备注")
    private String remark;

}
