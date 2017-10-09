package com.zhuxs.result.domain.enums;

/**
 * Created by shusesshou on 2017/10/9.
 */
public enum ActionType {
    ALL(0,"all"),
    CREATE(1,"create"),
    READ(2,"read"),
    UPDATE(3,"update"),
    DELETE(4,"delete")
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
