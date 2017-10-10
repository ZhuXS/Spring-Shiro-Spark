package com.zhuxs.result.dto;

import java.io.Serializable;

/**
 * Created by shusesshou on 2017/9/25.
 */
public class RoleDto implements Serializable{
    private long id;
    private String name;
    private String desc;

    public RoleDto() {
    }

    public RoleDto(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public RoleDto(long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleDto roleDto = (RoleDto) o;

        if (id != roleDto.id) return false;
        if (name != null ? !name.equals(roleDto.name) : roleDto.name != null) return false;
        return desc != null ? desc.equals(roleDto.desc) : roleDto.desc == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        return result;
    }
}
