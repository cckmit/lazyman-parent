package org.lazyman.starter.redisson.exception;

import lombok.Data;
import org.lazyman.common.constant.ErrCode;

@Data
public class RedissonMessageException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected Integer errCode;

    public RedissonMessageException(Integer errCode) {
        super();
        this.errCode = errCode;
    }

    public RedissonMessageException(ErrCode errCode) {
        super(errCode.getMessage());
        this.errCode = errCode.getErrCode();
    }

    public RedissonMessageException(String message) {
        super(message);
    }

    public RedissonMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedissonMessageException(ErrCode errCode, String message) {
        super(message);
        this.errCode = errCode.getErrCode();
    }

    public RedissonMessageException(ErrCode errCode, Throwable cause) {
        super(errCode.getMessage(), cause);
        this.errCode = errCode.getErrCode();
    }

    public RedissonMessageException(ErrCode errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode.getErrCode();
    }
}
