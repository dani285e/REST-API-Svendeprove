package com.api.rest.spring.Controller;

import com.api.rest.spring.Entity.Dto.UserDto;
import com.api.rest.spring.Entity.User;
import com.api.rest.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String index(){
        return "Greeting from spring";
    }


    @PostMapping("/add")
    public String addUser(@RequestParam String username, @RequestParam String role, @RequestParam String password, @RequestParam String email) {
        String randomSalt = UUID.randomUUID().toString();
        User user = new User();
        user.setUsername(username);
        user.setRole(role);
        user.setPassword(password);
        user.setSalt(randomSalt);
        user.setEmail(email);

        userRepository.save(user);
        return "Added new customer to repo!";
    }

    @GetMapping("/list")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/find/{id}")
    public UserDto findUserById(@PathVariable Integer id) {
        User temp = userRepository.findUserById(id);
        Integer uId = temp.getId();
        String uName = temp.getUsername();
        String uRole = temp.getRole();
        String uEmail = temp.getEmail();
        UserDto userDto = new UserDto(uId, uName, uRole, uEmail);
        return userDto;
    }
}
