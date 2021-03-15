package org.lazyman.core.dingtalk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlertMessage implements Serializable {
    private String moduleName;
    private String errCode;
    private String errMsg;
}
