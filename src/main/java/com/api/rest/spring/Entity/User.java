package com.api.rest.spring.Entity;

import javax.persistence.*;

import com.api.rest.spring.Entity.Enum.Role;
import com.api.rest.spring.Entity.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_tbl")
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private Role role;
    private String email;
    private String salt;
    private String password;
    private Boolean userStatus;

    @ManyToMany(mappedBy = "users")
    private List<Task> tasks = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }
}
