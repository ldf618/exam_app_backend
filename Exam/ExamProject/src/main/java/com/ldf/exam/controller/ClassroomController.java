/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.controller;

import com.ldf.exam.model.Classroom;
import com.ldf.exam.persistence.ClassroomRepo;
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
@RequestMapping(path = "/api/classroom", produces = "application/json")
public class ClassroomController {

    @Autowired
    private ClassroomRepo classroomRepo;
    
    @RequestMapping("/classrooms")
    public Iterable<Classroom> getClassrooms() {
        //throw new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden");
        return classroomRepo.findAll();
    }

    @GetMapping("/classroom/{id}")
    public ResponseEntity<Classroom> groupById(@PathVariable("id") Integer id) {
        Optional<Classroom> optClassroom = classroomRepo.findById(id);
        if (optClassroom.isPresent()) 
            return new ResponseEntity<>(optClassroom.get(), HttpStatus.OK);        
       return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }      
    
    @PostMapping(path ="classroom", consumes="application/json")
    public ResponseEntity<?> postClassroom(@RequestBody Classroom classroom) {
     if (classroom.getId()!=0) 
         return new ResponseEntity<>("classroom.id isnt allowed. Ids are db driven",HttpStatus.BAD_REQUEST);
     return new ResponseEntity<>(classroomRepo.save(classroom),HttpStatus.CREATED);
    }
    
    @GetMapping("/classroomStudentCourse/{studentId}/{courseId}")
    public ResponseEntity<Object[]> classroomByStudentIdAndCourseId
        (@PathVariable("studentId") Integer studentId, @PathVariable("courseId") Integer courseId) {
        Object[] classroomName = classroomRepo.findClassroomByStudentIdAndCourseId(studentId,courseId);
        return new ResponseEntity<>(classroomName, HttpStatus.OK);        
    }
        
    @GetMapping("/classroomConsultantCourse/{consultantId}/{courseId}")
    public ResponseEntity<Object[]> classroomByConsultantIdAndCourseId
        (@PathVariable("consultantId") Integer consultantId, @PathVariable("courseId") Integer courseId) {
        Object[] classroomName = classroomRepo.findClassroomByStudentIdAndCourseId(consultantId,courseId);
        return new ResponseEntity<>(classroomName, HttpStatus.OK);        
    }        

    @GetMapping("/classroomUserCourse/{consultantId}/{courseId}/{userType}")
    public ResponseEntity<Object[]> classroomByUserIdAndCourseId
        (@PathVariable("consultantId") Integer consultantId, @PathVariable("courseId") Integer courseId
        , @PathVariable("userType") String userType) {
            
        Object[] classroomName;
        if (userType.equalsIgnoreCase("student"))
            classroomName = classroomRepo.findClassroomByStudentIdAndCourseId(consultantId,courseId);
        else if (userType.equalsIgnoreCase("consultant"))
            classroomName = classroomRepo.findClassroomByConsultantIdAndCourseId(consultantId,courseId);
        else 
            classroomName = new Object[1];            
        System.out.println(classroomName);
        return new ResponseEntity<>(classroomName, HttpStatus.OK);        
    }           
    
}
