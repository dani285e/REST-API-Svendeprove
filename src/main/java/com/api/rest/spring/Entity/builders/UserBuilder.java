package com.api.rest.spring.Entity.builders;

import com.api.rest.spring.Entity.Enum.Role;
import com.api.rest.spring.Entity.User;
import com.api.rest.spring.WebApiHelper;

import java.util.Objects;
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
        user.setUsername(Objects.requireNonNull(username));
        user.setRole(Objects.requireNonNull(role));
        user.setPassword(Objects.requireNonNull(password));
        user.setSalt(Objects.requireNonNull(UUID.randomUUID().toString()));
        user.setEmail(Objects.requireNonNull(email));
        user.setUserStatus(Objects.requireNonNull(userStatus));
        // with variables

        return user;
    }




}
