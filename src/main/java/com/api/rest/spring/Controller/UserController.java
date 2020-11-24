package com.api.rest.spring.Controller;

import com.api.rest.spring.Entity.Dto.UserDto;
import com.api.rest.spring.Entity.Enum.Role;
import com.api.rest.spring.Entity.User;
import com.api.rest.spring.handlers.UserHandler;
import com.api.rest.spring.handlers.exceptions.ApiException;
import com.api.rest.spring.handlers.exceptions.AuthorizationException;
import com.api.rest.spring.handlers.exceptions.ValidationException;
import com.api.rest.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/register")
    public HttpStatus addUser(@RequestParam String username, @RequestParam String role, @RequestParam String password, @RequestParam String email) {
        try {
            UserHandler userHandler = new UserHandler(userRepository);
            userHandler.createUser(username, role, password, email);
            return HttpStatus.CREATED;
        } catch(ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorReason());
        } catch (Exception e) {
            System.out.println(String.format("Unexpected exception during call addUser: <%s>", e.getMessage()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete")
    public HttpStatus deleteUser(@RequestParam Integer id, @RequestParam Integer requestingUserId){
        try {
            UserHandler userHandler = new UserHandler(userRepository);
            userHandler.deleteUser(id, requestingUserId);
            return HttpStatus.OK;
        } catch (AuthorizationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorReason()); //e.getErrorReason takes error from userHandler
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getErrorReason());
        } catch (ApiException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorReason());
        }

    }

    @PostMapping("/deactivate")
    public HttpStatus deactivateUser(@RequestParam Integer id, @RequestParam Integer requestingUserId){
        try {
            UserHandler userHandler = new UserHandler(userRepository);
            userHandler.deactivateUser(id, requestingUserId);
            return HttpStatus.OK;
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorReason());
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorReason());
        }
    }

    @PostMapping("/activate")
    public HttpStatus updateUser(@RequestParam Integer id, @RequestParam Integer requestingUserId){
        try {
            UserHandler userHandler = new UserHandler(userRepository);
            userHandler.activateUser(id, requestingUserId);
            return HttpStatus.OK;
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorReason());
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorReason());
        }
    }
}
