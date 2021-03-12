package org.lazyman.boot.sys.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "vue路由meta返回对象")
public class VueRouterMetaVO implements Serializable {
    private String title;

    private String icon;

    private Boolean noCache;
}
