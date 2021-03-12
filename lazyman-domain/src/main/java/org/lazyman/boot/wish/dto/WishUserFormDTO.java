package org.lazyman.boot.wish.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.core.base.dto.BaseDTO;

import java.util.Date;

@ApiModel(value = "wish买家表单参数对象")
@Data
public class WishUserFormDTO extends BaseDTO {

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "个性头像")
    private String avatar;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "生日")
    private Date birth;

    @ApiModelProperty(value = "启用状态")
    private Boolean state;

    @ApiModelProperty(value = "微信开放ID")
    private String openId;
}
