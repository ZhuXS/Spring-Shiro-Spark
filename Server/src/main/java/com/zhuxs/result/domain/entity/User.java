package com.zhuxs.result.domain.entity;

import com.zhuxs.result.domain.enums.UserStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by shusesshou on 2017/9/19.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, name = "user_name")
    @NotNull
    private String username;

    @Column(unique = true, name = "name")
    @NotNull
    private String name;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "salt")
    @NotNull
    private String salt;

    @Column(name = "status")
    @NotNull
    private UserStatus status;



}
