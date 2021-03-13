package org.lazyman.boot.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lazyman.core.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>
 * APP用户
 * </p>
 *
 * @author wanglong
 * @since 2021-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_app_user")
@ApiModel(value = "APP用户实体")
public class AppUser extends BaseEntity {

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

    @ApiModelProperty(value = "微信统一ID")
    private String unionId;

    @ApiModelProperty(value = "微信开放ID")
    private String openId;
}
