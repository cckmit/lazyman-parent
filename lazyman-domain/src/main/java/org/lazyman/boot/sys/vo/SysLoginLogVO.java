package org.lazyman.boot.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.vo.BaseVO;
@Data
@ApiModel(value="系统登录日志返回对象")
public class SysLoginLogVO extends BaseVO {

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
