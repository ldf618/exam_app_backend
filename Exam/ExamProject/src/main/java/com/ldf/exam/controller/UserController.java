package com.ldf.exam.controller;

import com.ldf.exam.model.Consultant;
import com.ldf.exam.model.Exam;
import com.ldf.exam.model.Student;
import com.ldf.exam.model.User;
import com.ldf.exam.persistence.ConsultantRepo;
import com.ldf.exam.persistence.StudentRepo;
import com.ldf.exam.persistence.UserRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = "application/json")
//@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private ConsultantRepo consultantRepo;    
    
    @GetMapping("/user/{username}")
    public ResponseEntity<?> userByName(@PathVariable("username") String username) {
        User user = userRepo.findByUsername(username);
        if (user!=null){
            
            if (user instanceof Student){
                Student student = studentRepo.findById(user.getId()).get();
                if (student!=null){
                    student.getClassrooms();
                    return new ResponseEntity<>(student, HttpStatus.OK);        
                }
            }
            else if (user instanceof Consultant){
                    Consultant consultant = consultantRepo.findById(user.getId()).get();                    
                 if (consultant!=null){
                    return new ResponseEntity<>(consultant, HttpStatus.OK);        
                }
            }
            
        }
       return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
}
