package com.zhuxs.result.dto;

import com.zhuxs.result.domain.enums.ActionType;
import com.zhuxs.result.domain.enums.ResourceType;

import java.io.Serializable;

/**
 * Created by shusesshou on 2017/9/25.
 */
public class PermissionDto implements Serializable{
    private long id;
    private String name;
    private String resource;
    private ResourceType resourceType;
    private ActionType action;

    public PermissionDto() {
    }

    public PermissionDto(String name, String resource, ResourceType resourceType) {
        this.name = name;
        this.resource = resource;
        this.resourceType = resourceType;
    }

    public PermissionDto(long id, String name, String resource, ResourceType resourceType) {
        this.id = id;
        this.name = name;
        this.resource = resource;
        this.resourceType = resourceType;
    }

    public PermissionDto(long id, String name, String resource, ResourceType resourceType, ActionType action) {
        this.id = id;
        this.name = name;
        this.resource = resource;
        this.resourceType = resourceType;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionDto that = (PermissionDto) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (resource != null ? !resource.equals(that.resource) : that.resource != null) return false;
        if (resourceType != that.resourceType) return false;
        return action == that.action;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (resource != null ? resource.hashCode() : 0);
        result = 31 * result + (resourceType != null ? resourceType.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }
}
