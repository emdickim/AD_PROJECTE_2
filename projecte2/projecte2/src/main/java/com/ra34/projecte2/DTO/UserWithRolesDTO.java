package com.ra34.projecte2.DTO;

import java.util.List;

public class UserWithRolesDTO {
    private Long id;
    private String email;
    private Boolean status;
    private List<RoleDTO> roles;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public List<RoleDTO> getRoles() { return roles; }
    public void setRoles(List<RoleDTO> roles) { this.roles = roles; }
}