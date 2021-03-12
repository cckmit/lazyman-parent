package org.lazyman.common.exception;

import lombok.Data;
import org.lazyman.common.constant.ErrCode;

@Data
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected Integer errCode;

    public BizException(Integer errCode) {
        super();
        this.errCode = errCode;
    }

    public BizException(ErrCode errCode) {
        super(errCode.getMessage());
        this.errCode = errCode.getErrCode();
    }

    public BizException(ErrCode errCode, String message) {
        super(message);
        this.errCode = errCode.getErrCode();
    }

    public BizException(ErrCode errCode, Throwable cause) {
        super(errCode.getMessage(), cause);
        this.errCode = errCode.getErrCode();
    }

    public BizException(ErrCode errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode.getErrCode();
    }
}
