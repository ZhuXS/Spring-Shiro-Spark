package com.zhuxs.result.Exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhuxs.result.domain.enums.ErrorCode;

/**
 * Created by shusesshou on 2017/9/18.
 */
public class ResultException extends RuntimeException{
    private ErrorCode errorCode;

    public ResultException() {
    }

    public ResultException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ResultException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ResultException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @JsonIgnore
    public ResultException getResultExceptionWithoutCause(){
        return new ResultException(this.getMessage(),this.getErrorCode());
    }
}
