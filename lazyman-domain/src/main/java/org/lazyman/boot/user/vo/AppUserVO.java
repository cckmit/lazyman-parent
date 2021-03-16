package org.lazyman.boot.user.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lazyman.boot.base.vo.BaseVO;

import java.util.Date;

@Data
@ApiModel(value = "APP用户返回对象")
public class AppUserVO extends BaseVO {

    @ApiModelProperty(value = "昵称")
    @ExcelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机")
    @ExcelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "个性头像")
    @ExcelProperty(value = "个性头像")
    private String avatar;

    @ApiModelProperty(value = "性别")
    @ExcelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "生日")
    @ExcelProperty(value = "生日")
    private Date birth;
}
