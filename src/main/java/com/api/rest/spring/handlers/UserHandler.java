package com.api.rest.spring.handlers;

import com.api.rest.spring.Entity.Enum.Role;
import com.api.rest.spring.Entity.User;
import com.api.rest.spring.Entity.builders.UserBuilder;
import com.api.rest.spring.WebApiHelper;
import com.api.rest.spring.handlers.exceptions.ValidationException;
import com.api.rest.spring.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

public class UserHandler {

    private UserRepository userRepository;

    public User findUserById(Integer id) throws DataAccessException {
        //TODO Exception handling
        try {
            return userRepository.findUserById(id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO Ask Jonas what to return, return stack or return null?
            return null;
        }
    }

    public User findUserByUsername (String username) throws DataAccessException {
        //TODO Exception handling
        try {
            return userRepository.findUserByUsername(username);
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO Ask Jonas what to return, return stack or return null?
            return null;
        }

    }

    public UserHandler(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    public void validateUser(String username, String role, String password, String email) throws ValidationException {
        if (username == null || username.isEmpty())
            throw new ValidationException("Username is null or empty");

        if (userRepository.findUserByUsername(username) != null)
            throw new ValidationException("Username <" + username + "> is already used");

        if (role == null || Role.getRoleByName(role) == null)
            throw new ValidationException("Role <" + role + "> does not exist");

        Role foundRole = Role.getRoleByName(role);

        if (foundRole == Role.SUPER_ADMIN)
            throw new ValidationException("Role <" + foundRole + "> is not allowed");

        if (password == null || password.isEmpty())
            throw new ValidationException("Password is null or empty");

        if (password == username || password == email)
            throw new ValidationException("Password can't be the same as username or password");

        if (email == null || email.isEmpty())
            throw new ValidationException("Email is null or empty");



            //TODO ? mail validation ?

    }

    public void createUser(String username, String role, String password, String email) throws ValidationException {
        validateUser(username, role, password, email);

        UserBuilder userBuilder = UserBuilder.aUserBuilder();

        User build = userBuilder
                .withUsername(username)
                .withRole(Role.getRoleByName(role))
                .withSalt(UUID.randomUUID().toString())
                .withPassword(password)
                .withUserStatus(WebApiHelper.ACTIVATED_USER)
                .build();

        userRepository.save(build);
        //TODO talk to Jonas, does this need a return?
    }


    public void updateUser(Integer id) throws Exception {
        User user = findUserById(id);


        // if null, throw exception

        // use input variables to set new valeus on user

        userRepository.save(user);

    }

    public void  deleteUser(){

    }
    //TODO Delete

    public void deactivateUser(){

    }

    public void resetPassword(){

    }



}
