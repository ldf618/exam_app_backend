/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.controller;

import com.ldf.exam.model.Course;
import com.ldf.exam.persistence.CourseRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Lo
 *
 */
@RestController
@RequestMapping(path = "/api/course", produces = "application/json")
public class CourseController {

    @Autowired
    private CourseRepo courseRepo;
    
    @RequestMapping("/course")
    public Iterable<Course> getTest() {
        //throw new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden");
        return courseRepo.findAll();
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<Course> degreeById(@PathVariable("id") Integer id) {
        Optional<Course> optCourse = courseRepo.findById(id);
        if (optCourse.isPresent()) 
            return new ResponseEntity<>(optCourse.get(), HttpStatus.OK);        
       return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }      
    
    @PostMapping(path ="course", consumes="application/json")
    public ResponseEntity<?> postDegree(@RequestBody Course course) {
     if (course.getId()!=0) 
         return new ResponseEntity<>("course.id isnt allowed. Ids are db driven",HttpStatus.BAD_REQUEST);
     return new ResponseEntity<>(courseRepo.save(course),HttpStatus.CREATED);
    }
    
    @GetMapping("/coursesStudentDegree/{studentId}/{degreeId}")
    public ResponseEntity<List<Course>> coursesByStudentIdAndDegreeId
        (@PathVariable("studentId") Integer studentId, @PathVariable("degreeId") Integer degreeId) {
        List<Course> courseList = courseRepo.findCoursesByStudentIdAndDegreeId(studentId,degreeId);
        return new ResponseEntity<>(courseList, HttpStatus.OK);        
    }
        
    @GetMapping("/coursesConsultantDegree/{consultantId}/{degreeId}")
    public ResponseEntity<List<Course>> coursesByConsultantIdAndDegreeId
        (@PathVariable("consultantId") Integer consultantId, @PathVariable("degreeId") Integer degreeId) {
        List<Course> courseList = courseRepo.findCoursesByConsultantIdAndDegreeId(consultantId,degreeId);
        return new ResponseEntity<>(courseList, HttpStatus.OK);        
    }        
    
    @GetMapping("/coursesUserDegree/{userId}/{degreeId}/{userType}")
    public ResponseEntity<List<Course>> coursesByUserIdAndDegreeId
        (@PathVariable("userId") Integer userId, @PathVariable("degreeId") Integer degreeId,
         @PathVariable("userType") String userType) {
            
        List<Course> courseList;
        if (userType.equalsIgnoreCase("consultant"))
            courseList = courseRepo.findCoursesByConsultantIdAndDegreeId(userId,degreeId);
        else if (userType.equalsIgnoreCase("student"))
            courseList = courseRepo.findCoursesByStudentIdAndDegreeId(userId,degreeId);
        else
            courseList = new ArrayList();
        return new ResponseEntity<>(courseList, HttpStatus.OK);        
    }  
}
