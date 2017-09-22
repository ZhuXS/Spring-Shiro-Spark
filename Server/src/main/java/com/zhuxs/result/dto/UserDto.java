package com.zhuxs.result.dto;

/**
 * Created by shusesshou on 2017/9/22.
 */
public class UserDto {
    private String usrname;
    private String password;

    public UserDto() {
    }

    public UserDto(String usrname, String password) {
        this.usrname = usrname;
        this.password = password;
    }

    public String getUsrname() {
        return usrname;
    }

    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (usrname != null ? !usrname.equals(userDto.usrname) : userDto.usrname != null) return false;
        return password != null ? password.equals(userDto.password) : userDto.password == null;
    }

    @Override
    public int hashCode() {
        int result = usrname != null ? usrname.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
