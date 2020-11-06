package com.api.rest.spring.Controller;

        import com.api.rest.spring.Entity.Session;
        import com.api.rest.spring.Entity.User;
        import com.api.rest.spring.repository.SessionRepository;
        import com.api.rest.spring.repository.UserRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private SessionRepository sessionRepository;
    private UserRepository userRepository;

    @PostMapping("/login")
    public Session login(@RequestParam String username, @RequestParam String password){
        User user = userRepository.findUserByUsername(username);
        Integer userId = user.getId();
        Session session = sessionRepository.findSessionByUserId(userId);
        return session;
    }

    public String makeSession(Integer userId){


        return "";
    }

    @PostMapping("/logout")
    public Session logout(@RequestParam String sessionId){
        Session session = sessionRepository.findSessionById(sessionId);
        session = loginLogout(session, false);
        return session;
    }

    public Session loginLogout (Session objSession, boolean sessionStatus){
        Session session = objSession;
        if (sessionStatus){
            session.setSessionStatus(false);
        } else {
            session.setSessionStatus(true);
        }
        sessionRepository.save(session);
        return session;
    }
}
