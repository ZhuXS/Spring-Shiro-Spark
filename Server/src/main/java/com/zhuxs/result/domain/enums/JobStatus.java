package com.zhuxs.result.domain.enums;

/**
 * Created by shusesshou on 2017/9/18.
 */
public enum JobStatus {

    WAITING(0,"waiting"),
    READY(1,"ready"),
    RUNNING(2,"running"),
    BLOCK(3,"block"),
    FAILED(4,"failed"),
    SUCCESS(5,"success");

    private int status;
    private String desc;

    JobStatus(int status, String desc) {
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


    @Override
    public String toString() {
        return "JobStatus{" +
                "status=" + status +
                ", desc='" + desc + '\'' +
                '}';
    }
}
