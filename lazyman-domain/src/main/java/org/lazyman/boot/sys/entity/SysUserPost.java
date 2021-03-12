package org.lazyman.boot.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.lazyman.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户岗位
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_user_post")
@ApiModel(value="系统用户岗位实体")
public class SysUserPost extends BaseEntity {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "岗位ID")
    private Long postId;

}
