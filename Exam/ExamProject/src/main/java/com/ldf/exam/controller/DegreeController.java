/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.controller;

import com.ldf.exam.model.Degree;
import com.ldf.exam.persistence.DegreeRepo;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Lo
 *
 */
@RestController
@RequestMapping(path = "/api/degree", produces = "application/json")
@CrossOrigin(origins = "*")
public class DegreeController {

    @Autowired
    private DegreeRepo degreeRepo;

    @RequestMapping("/degrees")
    public Iterable<Degree> getTest() {
        //throw new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden");
        return degreeRepo.findAll();
    }

    @GetMapping("/degree/{id}")
    public ResponseEntity<Degree> degreeById(@PathVariable("id") Integer id) {
        Optional<Degree> optDegree = degreeRepo.findById(id);
        if (optDegree.isPresent()) 
            return new ResponseEntity<>(optDegree.get(), HttpStatus.OK);        
       return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/countbyname/{name}")
    public Integer countByName(@PathVariable("name") String name) {
        return degreeRepo.countByNameIgnoreCase(name);
    }
    
    @GetMapping("/existsbyname/{name}")
    public Boolean existsDegreeByName(@PathVariable("name") String name) {
        return degreeRepo.existsDegreeByNameIgnoreCase(name);
    }
    
    
    @PostMapping(path ="degree", consumes="application/json")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> postDegree(@RequestBody Degree degree) {
     //try {Thread.sleep(7000);} catch (InterruptedException ex) {}
     if (degree.getId()!=0) 
         return new ResponseEntity<>("degree.id isnt allowed. Ids are db driven",HttpStatus.BAD_REQUEST);
     return new ResponseEntity<>(degreeRepo.save(degree),HttpStatus.CREATED);
    }
}
