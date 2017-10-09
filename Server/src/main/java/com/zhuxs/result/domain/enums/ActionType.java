package com.zhuxs.result.domain.enums;

/**
 * Created by shusesshou on 2017/10/9.
 */
public enum ActionType {
    POST(0,"create"),
    GET(1,"read"),
    PUT(2,"update"),
    DELETE(3,"delete")
    ;
    private int type;
    private String desc;

    ActionType() {
    }

    ActionType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
