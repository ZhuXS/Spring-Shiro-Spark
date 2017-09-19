package com.zhuxs.result.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by shusesshou on 2017/9/19.
 */
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "description")
    private String desc;

    @Column(name = "available")
    @NotNull
    private Boolean available = Boolean.FALSE;

}
