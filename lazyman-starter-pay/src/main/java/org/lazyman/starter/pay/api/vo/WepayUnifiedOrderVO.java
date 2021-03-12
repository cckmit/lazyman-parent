package org.lazyman.starter.pay.api.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class WepayUnifiedOrderVO {
    @XmlElement(name = "return_code")
    private String return_code;
    @XmlElement(name = "return_msg")
    private String return_msg;
    @XmlElement(name = "appid")
    private String appid;
    @XmlElement(name = "mch_id")
    private String mch_id;
    @XmlElement(name = "device_info")
    private String device_info;
    @XmlElement(name = "nonce_str")
    private String nonce_str;
    @XmlElement(name = "sign")
    private String sign;
    @XmlElement(name = "result_code")
    private String result_code;
    @XmlElement(name = "err_code")
    private String err_code;
    @XmlElement(name = "err_code_des")
    private String err_code_des;
    @XmlElement(name = "trade_type")
    private String trade_type;
    @XmlElement(name = "prepay_id")
    private String prepay_id;
    @XmlElement(name = "code_url")
    private String code_url;
}
