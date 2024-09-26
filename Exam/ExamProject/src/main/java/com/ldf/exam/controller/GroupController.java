/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.controller;

import com.ldf.exam.model.Group;
import com.ldf.exam.persistence.GroupRepo;
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
@RequestMapping(path = "/api/group", produces = "application/json")
public class GroupController {

    @Autowired
    private GroupRepo groupRepo;
    
    @RequestMapping("/groups")
    public Iterable<Group> getGroups() {
        //throw new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden");
        return groupRepo.findAll();
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<Group> groupById(@PathVariable("id") Integer id) {
        Optional<Group> optGroup = groupRepo.findById(id);
        if (optGroup.isPresent()) 
            return new ResponseEntity<>(optGroup.get(), HttpStatus.OK);        
       return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }      
    
    @PostMapping(path ="group", consumes="application/json")
    public ResponseEntity<?> postGroup(@RequestBody Group group) {
     if (group.getId()!=0) 
         return new ResponseEntity<>("group.id isnt allowed. Ids are db driven",HttpStatus.BAD_REQUEST);
     return new ResponseEntity<>(groupRepo.save(group),HttpStatus.CREATED);
    }
    
    @GetMapping("/groupStudentCourse/{studentId}/{courseId}")
    public ResponseEntity<Object[]> groupByStudentIdAndCourseId
        (@PathVariable("studentId") Integer studentId, @PathVariable("courseId") Integer courseId) {
        Object[] group = groupRepo.findGroupByStudentIdAndCourseId(studentId,courseId);
        return new ResponseEntity<>(group, HttpStatus.OK);        
    }
    
}
