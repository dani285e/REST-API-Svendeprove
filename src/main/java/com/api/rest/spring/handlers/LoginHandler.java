package com.api.rest.spring.handlers;

import com.api.rest.spring.Entity.User;
import com.api.rest.spring.handlers.exceptions.AuthorizationException;
import com.api.rest.spring.handlers.exceptions.ValidationException;
import com.api.rest.spring.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LoginHandler {

    private UserRepository userRepository;

    public LoginHandler(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }
    public String Login(String username, String password) throws ValidationException, AuthorizationException {
        ValidateLogin(username, password);

        return getJWTToken(username);
    }


    private void ValidateLogin(String username, String pwd) throws ValidationException, AuthorizationException {
        if (username == null)
            throw new ValidationException("Missing username");
        if (pwd == null)
            throw new ValidationException("Missing password");
        User user = userRepository.findUserByUsername(username);
        if (user.getPassword() == null)
            throw new ValidationException("User is missing password");
        //TODO check with salt
//        String hashPassword = hashPasswordWithSalt(pwd, user.getSalt());
//        if (!user.getPassword().equals(hashPassword))
//            throw new AuthorizationException("User failed to login");
    }

    private String hashPasswordWithSalt(String password, String salt) {
        String hashPlusSalt = password + salt;
        String hashPassword = new SCryptPasswordEncoder().encode(hashPlusSalt);

        return hashPassword;
    }

    private String getJWTToken(String username){
        User user =  userRepository.findUserByUsername(username);
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(user.getRole().toString()); //User Role

        String token = Jwts
                .builder()
                .setId("tokenJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
