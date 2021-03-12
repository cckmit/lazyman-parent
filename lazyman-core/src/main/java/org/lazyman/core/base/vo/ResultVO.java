package org.lazyman.core.base.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.constant.ErrCode;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "接口返回对象", description = "接口返回对象")
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "成功标志")
    private Boolean success;

    @ApiModelProperty(value = "返回处理消息")
    private String message;

    @ApiModelProperty(value = "返回错误代码")
    private Integer code;

    @ApiModelProperty(value = "返回数据对象")
    private T result;

    @ApiModelProperty(value = "时间戳")
    private Long timestamp;

    private static <T> ResultVO<T> ok(T data, String message) {
        ResultVO<T> resultVO = new ResultVO<T>();
        resultVO.setSuccess(true);
        resultVO.setResult(data);
        resultVO.setMessage(message);
        resultVO.setTimestamp(System.currentTimeMillis());
        return resultVO;
    }

    public static <T> ResultVO<T> ok(T data) {
        return ok(data, null);
    }

    public static <T> ResultVO<T> ok() {
        return ok(null, CommonErrCode.OK.getMessage());
    }

    public static ResultVO error(Integer errCode, String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setSuccess(false);
        resultVO.setCode(errCode);
        resultVO.setMessage(message);
        resultVO.setTimestamp(System.currentTimeMillis());
        return resultVO;
    }

    public static ResultVO error(ErrCode errCode) {
        return error(errCode.getErrCode(), errCode.getMessage());
    }

    public static ResultVO error(ErrCode errCode, String message) {
        return error(errCode.getErrCode(), message);
    }
}