package com.ldf.exam.controller;

import com.ldf.exam.*;
import com.ldf.exam.model.User;
import com.ldf.exam.persistence.UserRepo;
import com.ldf.exam.security.JWTUtil;
import com.ldf.exam.security.LoginCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody User user) {
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        user = userRepo.save(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/loginJson")
    public Map<String, Object> loginHandlerJson(@RequestBody LoginCredentials body, HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken authInputToken
                    = new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());

            Authentication a = authManager.authenticate(authInputToken);

            //String token = jwtUtil.generateToken(body.getUsername());
            String token = jwtUtil.generateToken(body.getUsername(), (List<GrantedAuthority>) a.getAuthorities());

            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException authExc) {
            //throw new RuntimeException("Invalid Login Credentials");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Login Credentials");
            return Collections.singletonMap("error", "Invalid Login Credentials");
        }
    }

    @PostMapping("/loginCookie")
    public ResponseEntity<?> loginHandlerCookie(@RequestBody LoginCredentials body, HttpServletResponse response) {
        try {
            System.out.println("loginCookie");
            UsernamePasswordAuthenticationToken authInputToken
                    = new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());

            Authentication a = authManager.authenticate(authInputToken);

            //String token = jwtUtil.generateToken(body.getUsername());
            ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(body.getUsername(), (List<GrantedAuthority>) a.getAuthorities());

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(true);//"Login succeeded!!");

        } catch (AuthenticationException authExc) {
            //throw new RuntimeException("Invalid Login Credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);//"Invalid Login Credentials");
        }
    }

}
