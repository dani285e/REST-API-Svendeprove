package com.api.rest.spring.Controller;

import com.api.rest.spring.Entity.Dto.UserDto;
import com.api.rest.spring.Entity.Enum.Role;
import com.api.rest.spring.Entity.User;
import com.api.rest.spring.handlers.UserHandler;
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

    @RequestMapping("/hello")
    public String index(){
        return "Greeting from spring";
    }



    //TODO Test function
    @PostMapping("/register")
    public Object addUser(@RequestParam String username, @RequestParam String role, @RequestParam String password, @RequestParam String email) {
        try {
            UserHandler userHandler = new UserHandler(userRepository);
            userHandler.createUser(username, role, password, email);
            return HttpStatus.CREATED;
        } catch(ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getRejectReason());
        } catch (Exception e) {
            System.out.println(String.format("Unexpected exception during call addUser: <%s>", e.getMessage()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/findByName")
    public UserDto findUserByUsername(@RequestParam String username) {
//        User user = new User();
//        user.setUsername("testUser");
//        user.setRole(Role.EMPLOYEE);
//        user.setPassword("password");
//        user.setSalt("randomSalt");
//        user.setEmail("email");
//        user.setUserStatus(true);
//        userRepository.save(user);

        User temp = userRepository.findUserByUsername(username);
        Integer uId = temp.getId();
        String uName = temp.getUsername();
        Role uRole = temp.getRole();
        String uEmail = temp.getEmail();
         UserDto userDto = new UserDto(uId, uName, uRole.getName(), uEmail);
        return userDto; //userDto;
    }


//    @GetMapping("/find/{id}")
//    public UserDto findUserById(@PathVariable Integer id) {
//        User temp = userRepository.findUserById(id);
//        Integer uId = temp.getId();
//        String uName = temp.getUsername();
//        String uRole = temp.getRole();
//        String uEmail = temp.getEmail();
//        UserDto userDto = new UserDto(uId, uName, uRole, uEmail);
//        return userDto;
//    }
}
