package org.lazyman.starter.pay.api.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class WepayCallbackVO {
    @XmlElement(name = "return_code")
    private String return_code;
    @XmlElement(name = "return_msg")
    private String return_msg;
}
