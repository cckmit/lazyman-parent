package org.lazyman.common.constant;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommonErrCode implements ErrCode {
    OK(200, "请求处理成功"),
    BAD_REQUEST(400, "请求或请求参数无效"),
    UNAUTHORIZED(401, "未认证或认证失效"),
    FORBIDDEN(403, "请求被拒绝"),
    NOT_FOUND(404, "请求资源未找到"),
    REPEAT_CREATE(405, "资源已存在，不能重复创建"),
    REQUEST_FREQUENTLY(406, "请求过于频繁"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    REQUEST_TIMEOUT(504, "请求超时");

    private Integer errCode;
    private String message;

    public static CommonErrCode findByErrCode(Integer errCode) {
        if (ObjectUtil.isNull(errCode)) {
            return null;
        }
        for (CommonErrCode commonErrCode : values()) {
            if (errCode.intValue() == commonErrCode.errCode.intValue()) {
                return commonErrCode;
            }
        }
        return null;
    }
}
