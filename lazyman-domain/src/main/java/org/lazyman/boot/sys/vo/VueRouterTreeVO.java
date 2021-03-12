package org.lazyman.boot.sys.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "vue路由树返回对象")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VueRouterTreeVO implements Serializable {
    private String name;

    private String path;

    private Boolean hidden;

    private String redirect;

    private String component;

    private Boolean alwaysShow;

    private VueRouterMetaVO meta;

    @ApiModelProperty(value = "叶子节点列表")
    private List<VueRouterTreeVO> children;
}
