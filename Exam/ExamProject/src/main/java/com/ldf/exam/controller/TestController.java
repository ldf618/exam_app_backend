/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ldf.exam.controller;

import com.ldf.exam.model.Student;
import com.ldf.exam.persistence.StudentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ldiez
 */
@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/test")
public class TestController {
    
    Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private StudentRepo studentRepo;
    
    @GetMapping("/getUserAliciaVerdu")
    public ResponseEntity<?> registerHandler() {
        logger.info("/getUserAliciaVerdu");
        Student s = studentRepo.findByUsername("AliciaVerdu");
        return ResponseEntity.ok().body(s);
    }        
    
}
