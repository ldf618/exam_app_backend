/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ldf.exam.persistence;

import com.ldf.exam.model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ldiez
 */
public interface UserRepo
    extends CrudRepository<User, Integer> {
    
    //Optional<User> findByUsername(String username);
    User findByUsername(String username);
    
}

