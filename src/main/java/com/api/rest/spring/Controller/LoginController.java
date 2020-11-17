package com.api.rest.spring.Controller;
        import java.util.Date;
        import java.util.List;
        import java.util.stream.Collectors;

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
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;

        import com.api.rest.spring.Entity.Dto.LoginDto;
        import io.jsonwebtoken.Jwts;
        import io.jsonwebtoken.SignatureAlgorithm;
        import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/security")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/user")
    public LoginDto login(@RequestParam("user") String username, @RequestParam("password") String pwd){
        //TODO Validate user credenticals
        try {
            LoginHandler loginHandler = new LoginHandler(userRepository);
            System.out.println(String.format("Got login attempt with username:%s and password:%s", username, pwd));
            String token = loginHandler.Login(username, pwd);
            LoginDto loginDto = new LoginDto(username, token);
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
