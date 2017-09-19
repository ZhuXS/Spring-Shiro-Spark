package com.zhuxs.result.domain.enums;

/**
 * Created by shusesshou on 2017/9/19.
 */
public enum UserStatus {

    CREATE(1,"create without certification"),
    NORMAL(2,"normal"),
    LOCKED(3,"locked");

    private int status;
    private String desc;

    UserStatus() {
    }

    UserStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
