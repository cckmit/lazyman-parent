package org.lazyman.boot.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseTenantEntity<T extends BaseTenantEntity<?>> extends BaseEntity<T> {
    @ApiModelProperty(value = "租户ID", hidden = true)
    private Long tenantId;
}
