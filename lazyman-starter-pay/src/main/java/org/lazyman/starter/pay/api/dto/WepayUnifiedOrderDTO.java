package org.lazyman.starter.pay.api.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class WepayUnifiedOrderDTO {
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
    @XmlElement(name = "sign_type")
    private String sign_type;
    @XmlElement(name = "body")
    private String body;
    @XmlElement(name = "product_id")
    private String product_id;
    @XmlElement(name = "detail")
    private String detail;
    @XmlElement(name = "attach")
    private String attach;
    @XmlElement(name = "out_trade_no")
    private String out_trade_no;
    @XmlElement(name = "fee_type")
    private String fee_type;
    @XmlElement(name = "total_fee")
    private String total_fee;
    @XmlElement(name = "spbill_create_ip")
    private String spbill_create_ip;
    @XmlElement(name = "time_start")
    private String time_start;
    @XmlElement(name = "time_expire")
    private String time_expire;
    @XmlElement(name = "goods_tag")
    private String goods_tag;
    @XmlElement(name = "notify_url")
    private String notify_url;
    @XmlElement(name = "trade_type")
    private String trade_type;
    @XmlElement(name = "openid")
    private String openid;
    @XmlElement(name = "limit_pay")
    private String limit_pay;

}
