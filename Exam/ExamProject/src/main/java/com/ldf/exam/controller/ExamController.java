/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.controller;

import com.ldf.exam.controller.dto.SearchExamDTO;
import com.ldf.exam.model.Exam;
import com.ldf.exam.persistence.ExamRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import static com.ldf.exam.persistence.ExamSpecification.*;

/**
 *
 * @author Lo
 *
 */
@RestController
@RequestMapping(path = "/api/exam", produces = {"application/json", "text/xml"})
@CrossOrigin(origins = "*")
public class ExamController {

    @Autowired
    private ExamRepo examRepo;
    
    @RequestMapping("/exams")
    public List<Exam> findAll() {
        return examRepo.findAll();
    }

    @GetMapping("/exams/{id}")
    public ResponseEntity<Exam> examById(@PathVariable("id") Integer id) {
        Optional<Exam> optExam = examRepo.findById(id);
        if (optExam.isPresent()) 
            return new ResponseEntity<>(optExam.get(), HttpStatus.OK);        
       return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    @PostMapping(path ="exam", consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Exam saveExam(@RequestBody Exam exam/*,HttpEntity<String> httpEntity*/) {
        //System.out.println(httpEntity.getBody());
        System.out.println(exam);
//        List<ExamQuestion> eq = exam.getExamQuestions();
//        exam.setExamQuestions(new ArrayList());
//        eq.forEach(q->{q.setExam(exam);});
        return examRepo.save(exam);
    }
    
    @PostMapping(path ="examSearch", consumes="application/json")
    public List<Exam> findByParams(@RequestBody SearchExamDTO searchExamDTO/*,HttpEntity<String> httpEntity*/) {       
        System.out.println(searchExamDTO);
       return  examRepo.findAll(
               createSpecification(
                       searchExamDTO.getName(), 
                       searchExamDTO.getType(), 
                       searchExamDTO.getPublished(), 
                       searchExamDTO.getStartDate(), 
                       searchExamDTO.getEndDate())
       );       
    }
    
}
