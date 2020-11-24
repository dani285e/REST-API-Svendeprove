package com.api.rest.spring.Entity.Enum;

import java.util.Arrays;

public enum Role {
    SUPER_ADMIN("Super_Admin"),
    ADMIN("Admin"),
    EMPLOYEE("Employee"),
    CUSTOMER("Customer");

    private String name;

    private Role(String name) {
        this.name = name;
    }

    public static Role getRoleByName(String name) {
        return valueOf(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
