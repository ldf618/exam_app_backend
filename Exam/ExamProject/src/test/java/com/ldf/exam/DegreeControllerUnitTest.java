/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ldf.exam;

import com.ldf.exam.controller.DegreeController;
import com.ldf.exam.persistence.DegreeRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
/**
 *
 * @author ldiez
 */

@SpringBootTest
//@RunWith(SpringRunner.class)
public class DegreeControllerUnitTest {
 
    @Autowired
    private DegreeController controller;
    @MockBean
    private DegreeRepo repo;
    
    @Test
    public void itShouldReturnTheServiceValueWith200StatusCode() {
        when(repo.countByNameIgnoreCase("abc")).thenReturn(10);
        int count = controller.countByName("abc");

        //Assertions.assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(10, count);
    }

}
    

