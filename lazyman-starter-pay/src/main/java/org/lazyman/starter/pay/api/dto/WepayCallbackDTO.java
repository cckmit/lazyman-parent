package org.lazyman.starter.pay.api.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class WepayCallbackDTO {
    @XmlElement(name = "return_code")
    private String return_code;
    @XmlElement(name = "return_msg")
    private String return_msg;
    @XmlElement(name = "out_trade_no")
    private String out_trade_no;
    @XmlElement(name = "transaction_id")
    private String transaction_id;
    @XmlElement(name = "total_fee")
    private String total_fee;
    @XmlElement(name = "result_code")
    private String result_code;
    @XmlElement(name = "err_code")
    private String err_code;
    @XmlElement(name = "err_code_des")
    private String err_code_des;
}
