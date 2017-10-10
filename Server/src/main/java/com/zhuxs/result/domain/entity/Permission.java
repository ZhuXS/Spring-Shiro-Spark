package com.zhuxs.result.domain.entity;

import com.zhuxs.result.domain.enums.ActionType;
import com.zhuxs.result.domain.enums.ResourceType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shusesshou on 2017/9/19.
 */
@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    /**
     * 资源url
     */
    @Column(name = "url")
    @NotNull
    private String resource;

    /**
     * 操作类型
     */
    @Column(name = "action")
    @NotNull
    private ActionType action;

    @Column(name = "type")
    @NotNull
    private ResourceType type;

    @ManyToMany(mappedBy = "permissions",fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(mappedBy = "permissions",fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    public Permission() {
    }

    public Permission(String name, String resource, ResourceType type) {
        this.name = name;
        this.resource = resource;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (resource != null ? !resource.equals(that.resource) : that.resource != null) return false;
        if (action != that.action) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (resource != null ? resource.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
