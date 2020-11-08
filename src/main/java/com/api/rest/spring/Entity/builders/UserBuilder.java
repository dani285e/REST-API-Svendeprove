package com.api.rest.spring.Entity.builders;

import com.api.rest.spring.Entity.Enum.Role;
import com.api.rest.spring.Entity.User;
import com.api.rest.spring.WebApiHelper;

import java.util.UUID;

public class UserBuilder {
    
    private String username;
    private Role role;
    private String email;
    private String salt;
    private String password;
    private Boolean userStatus;

    public static UserBuilder aUserBuilder() {
        return new UserBuilder();
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
        return this;
    }




    public User build() {
        User user = new User();
        user.setUsername(username);
        user.setRole(role);
        user.setPassword(password);
        user.setSalt(UUID.randomUUID().toString());
        user.setEmail(email);
        user.setUserStatus(userStatus);
        // with variables

        return user;
    }




}
