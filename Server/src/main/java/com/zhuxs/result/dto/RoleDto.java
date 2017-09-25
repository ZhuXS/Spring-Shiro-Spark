package com.zhuxs.result.dto;

/**
 * Created by shusesshou on 2017/9/25.
 */
public class RoleDto {
    private String name;
    private String desc;

    public RoleDto() {
    }

    public RoleDto(String name, String desc) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleDto roleDto = (RoleDto) o;

        if (name != null ? !name.equals(roleDto.name) : roleDto.name != null) return false;
        return desc != null ? desc.equals(roleDto.desc) : roleDto.desc == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        return result;
    }
}
