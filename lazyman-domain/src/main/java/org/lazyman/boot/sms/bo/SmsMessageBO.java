package org.lazyman.boot.sms.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author wanglong
 */
@Data
public class SmsMessageBO implements Serializable {
    private Integer smsType;
    private Long batchId;
    private List<String> mobiles;
    private String content;
    private Date sendTime;
    private String subPort;
}
