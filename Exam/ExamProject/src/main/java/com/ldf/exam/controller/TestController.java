/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.controller;

import com.ldf.exam.model.Exam;
import com.ldf.exam.persistence.ExamRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Lo
 **/

@RestController
public class TestController {
 
    @Autowired
    private ExamRepo examRepo;
 
    @RequestMapping("/exam")
    public List<Exam> getTest() {
 
        return examRepo.findAll();
    }
} 
 