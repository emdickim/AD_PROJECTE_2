package com.ra34.projecte2.DTO;

import java.util.List;

public class AddRolesToUserRequest {
    private Long userId;
    private List<Long> roleIds;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public List<Long> getRoleIds() { return roleIds; }
    public void setRoleIds(List<Long> roleIds) { this.roleIds = roleIds; }
}