package com.ra34.projecte2.DTO;

import java.util.List;

public class UserResponseDTO {
    private Long id;
    private String email;
    private List<RoleDTO> roles;

    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public UserResponseDTO(Long id, String email, List<RoleDTO> roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<RoleDTO> getRoles() { return roles; }
    public void setRoles(List<RoleDTO> roles) { this.roles = roles; }
}
