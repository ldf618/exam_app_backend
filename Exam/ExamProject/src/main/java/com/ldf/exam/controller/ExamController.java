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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
    
    @PostMapping(path ="deleteExam", consumes="application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExam(@RequestBody Exam exam/*,HttpEntity<String> httpEntity*/) {
        System.out.println(exam);
        examRepo.delete(exam);
    }
    
    @PostMapping(path ="test", consumes="application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void test(@RequestBody Exam exam/*,HttpEntity<String> httpEntity*/) {

    }
    
    
    @PostMapping(path ="updatePublicationDate", consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public int publicateExam(@RequestBody Exam exam) {
        System.out.println(exam);
        return examRepo.setExamPublicationDateById(exam.getPublicationDate(),exam.getId());
    }
    
    @PostMapping(path ="examSearch", consumes="application/json")
    public Page<Exam> findByParams(@RequestBody SearchExamDTO searchExamDTO/*,HttpEntity<String> httpEntity*/) {       
        System.out.println(searchExamDTO);
        
        PageRequest pr;
        
        if (searchExamDTO.getPageNumber()==null)
            searchExamDTO.setPageNumber(0); 
        
        if (searchExamDTO.getPageSize()==null)
            pr =  PageRequest.ofSize(10);
        else 
            pr =  PageRequest.of(searchExamDTO.getPageNumber(), searchExamDTO.getPageSize()); 
        
        Page<Exam> page = examRepo.findAll(
               createSpecification(
                       searchExamDTO.getName(), 
                       searchExamDTO.getType(), 
                       searchExamDTO.getPublished(), 
                       searchExamDTO.getStartDate(), 
                       searchExamDTO.getEndDate(),
                       searchExamDTO.getCourse()),
                       pr
       );       
        return page;
    }
    
}
