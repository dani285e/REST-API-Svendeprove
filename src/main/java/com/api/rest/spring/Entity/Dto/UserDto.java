package com.api.rest.spring.Entity.Dto;

import com.api.rest.spring.Entity.Enum.Role;

public class UserDto {
    private Integer id;
    private String username;
    private Role role;
    private String email;

    public UserDto(Integer id, String username, Role role, String email) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
