package com.ra34.projecte2.DTO;

import java.util.List;

public class AddRolesRequest {
    private List<Long> roleIds;

    public AddRolesRequest() {}

    public AddRolesRequest(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public List<Long> getRoleIds() { return roleIds; }
    public void setRoleIds(List<Long> roleIds) { this.roleIds = roleIds; }
}
