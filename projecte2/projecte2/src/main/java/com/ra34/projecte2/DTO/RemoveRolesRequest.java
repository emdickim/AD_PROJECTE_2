package com.ra34.projecte2.DTO;

import java.util.List;

public class RemoveRolesRequest {
    private List<Long> roleIds;

    public RemoveRolesRequest() {}

    public RemoveRolesRequest(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public List<Long> getRoleIds() { return roleIds; }
    public void setRoleIds(List<Long> roleIds) { this.roleIds = roleIds; }
}
