package com.api.rest.spring.Controller;
        import java.util.Date;
        import java.util.List;
        import java.util.stream.Collectors;

        import com.api.rest.spring.Entity.Dto.LoginFormDTO;
        import com.api.rest.spring.Entity.User;
        import com.api.rest.spring.handlers.LoginHandler;
        import com.api.rest.spring.handlers.UserHandler;
        import com.api.rest.spring.handlers.exceptions.AuthorizationException;
        import com.api.rest.spring.handlers.exceptions.ValidationException;
        import com.api.rest.spring.repository.UserRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.security.core.AuthenticationException;
        import org.springframework.security.core.GrantedAuthority;
        import org.springframework.security.core.authority.AuthorityUtils;
        import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
        import org.springframework.web.bind.annotation.*;

        import com.api.rest.spring.Entity.Dto.LoginDto;
        import io.jsonwebtoken.Jwts;
        import io.jsonwebtoken.SignatureAlgorithm;
        import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/security")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
    public LoginDto login(@RequestBody LoginFormDTO loginFormDTO){
        String password = loginFormDTO.getPassword();
        System.out.println("Setting user");
        User user = userRepository.findUserByUsername(loginFormDTO.getUsername());
        String username = user.getUsername();
        System.out.println("User sat");
        System.out.println(user);
        //TODO add hashing
        //TODO Validate user credentials
        try {
            LoginHandler loginHandler = new LoginHandler(userRepository);
            System.out.println(String.format("Got login attempt with username:%s and password:%s", username, password));
            String token = loginHandler.Login(username, password);
            System.out.println("Set DTO");
            LoginDto loginDto = new LoginDto(user.getId(), username, token);
            System.out.println(loginDto);
            System.out.println(HttpStatus.values());
            return loginDto;
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Login failed", e);
        }
    }



//    @Autowired
//    private SessionRepository sessionRepository;
//    private UserRepository userRepository;
//
//    @PostMapping("/login")
//    public Session login(@RequestParam String username, @RequestParam String password){
//        User user = userRepository.findUserByUsername(username);
//        Integer userId = user.getId();
//        Session session = sessionRepository.findSessionByUserId(userId);
//        return session;
//    }
//
//    public String makeSession(Integer userId){
//        return "";
//    }
//
//    @PostMapping("/logout")
//    public Session logout(@RequestParam String sessionId){
//        Session session = sessionRepository.findSessionById(sessionId);
//        session = loginLogout(session, false);
//        return session;
//    }
//
//    public Session loginLogout (Session objSession, boolean sessionStatus){
//        Session session = objSession;
//        if (sessionStatus){
//            session.setSessionStatus(false);
//        } else {
//            session.setSessionStatus(true);
//        }
//        sessionRepository.save(session);
//        return session;
//    }
}
