package com.zhuxs.result.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by shusesshou on 2017/9/18.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  ErrorCode {
    ERROR(40000,"error"),
    NOTAUTHC(40100,"not authc"),
    NOTAUTHZ(40101,"not authz"),
    USERNAMEORPASSWORD(40401,"Username or Password Not Correct");

    private final int code;
    private final String desc;

    ErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
