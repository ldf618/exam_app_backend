package com.ldf.exam.controller;

import com.ldf.exam.model.Consultant;
import com.ldf.exam.model.Student;
import com.ldf.exam.model.User;
import com.ldf.exam.persistence.StudentRepo;
import com.ldf.exam.persistence.ConsultantRepo;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private ConsultantRepo consultantRepo;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerHandler(@RequestBody User user) {
        if (user.getType().equals("Student")) {
            return registerStudent(user);
        } else {
            return registerConsultant(user);
        }
    }
    
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder){
        
        // Convert multipart object to byte[]
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        
    }
    @PostMapping(value = "/register2", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> registerHandler2(@ModelAttribute User user) {
        if (user.getType().equals("Student")) {
            return registerStudent(user);
        } else {
            return registerConsultant(user);
        }
    }
    
   @ExceptionHandler(DataIntegrityViolationException.class)
   public ResponseEntity handleDuplicateUsername(DataIntegrityViolationException e) {
     if (e.getMostSpecificCause().getMessage().contains("username"))//.contains("unique"))
        return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe. Pruebe otro");
     else
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMostSpecificCause());
   }
   
   @ExceptionHandler(MaxUploadSizeExceededException.class)
   public ResponseEntity handlePhoto(MaxUploadSizeExceededException e) {
       System.out.println("MaxUploadSizeExceededException");
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("No se ha podido registrar el usuario. La foto es demasido grande > 10MB");
   }

    private ResponseEntity<?> registerStudent(User user) {

        Student student = new Student();
        student.copyFromUser(user);
        student.setPassword(passwordEncoder.encode(user.getPassword()));
        student = studentRepo.save(student);

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) student.getAuthorities();
        ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(user.getUsername(), authorities);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(student);
    }
    
    private ResponseEntity<?> registerConsultant(User user) {

        Consultant consultant = new Consultant();
        consultant.copyFromUser(user);
        consultant.setPassword(passwordEncoder.encode(user.getPassword()));
        consultant = consultantRepo.save(consultant);

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) consultant.getAuthorities();
        ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(user.getUsername(), authorities);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(consultant);
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
