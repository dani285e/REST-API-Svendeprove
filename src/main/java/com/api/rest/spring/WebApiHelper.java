package com.api.rest.spring;

import java.util.Arrays;
import java.util.List;

public class WebApiHelper {

    public static final String[] UNAUTHORIZED_PATHS = {"/security/user", "/users/register"};

    public static final Boolean ACTIVATED_USER = true; //True = User is activated. False = User is deactivated
}
