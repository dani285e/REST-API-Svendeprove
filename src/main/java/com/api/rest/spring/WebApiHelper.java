package com.api.rest.spring;

import com.api.rest.spring.Entity.Enum.Role;

import java.util.List;

public class WebApiHelper {

    public static final String[] UNAUTHORIZED_PATHS = {"/security/user", "/users/register"};

    public static final List<Role> ADMIN_ROLES = List.of(Role.ADMIN, Role.SUPER_ADMIN);

    public static final List<Role> PROTECTED_USER_ROLES = List.of(Role.SUPER_ADMIN);

    public static final Boolean ACTIVATED_USER = true; //STANDARD for when user is made; True = User is activated. False = User is deactivated
}
