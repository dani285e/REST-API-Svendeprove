package com.api.rest.spring.Entity;

import javax.persistence.*;

import com.api.rest.spring.Entity.Enum.Role;
import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_tbl")
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    private String username;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;
    @NotNull
    private String email;
    @NotNull
    private String salt;
    @NotNull
    private String password;
    @NotNull
    private Boolean userStatus;

    @ManyToMany(mappedBy = "users")
    private List<Task> tasks = new ArrayList<>();


    public Integer getId() {
        return id;
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

    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }

    public Boolean getUserStatus() {
        return userStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }

}
