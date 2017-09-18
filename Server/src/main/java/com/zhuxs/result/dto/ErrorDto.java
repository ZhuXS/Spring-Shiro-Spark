package com.zhuxs.result.dto;

import com.zhuxs.result.domain.enums.ErrorCode;

/**
 * Created by shusesshou on 2017/9/18.
 */
public class ErrorDto {
    private String status;
    private ErrorCode errorCode;
    private String url;
    private String msg;

    public ErrorDto() {
    }

    public ErrorDto(String status, ErrorCode errorCode, String url, String msg) {
        this.status = status;
        this.errorCode = errorCode;
        this.url = url;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
