/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ldf.exam;

import com.ldf.exam.model.Degree;
import com.ldf.exam.persistence.DegreeRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

/**
 *
 * @author ldiez
 */

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
@Sql(config = @SqlConfig(encoding = "UTF-8"), scripts = "/sql/test-degree.sql")
public class DegreeRepoTest {
    
    @Autowired
    DegreeRepo degreeRepo;
 
    @Test
    void testFindAll() {
        List<Degree> allDegrees =  new ArrayList<>();
        degreeRepo.findAll().forEach(allDegrees::add);
 
        assertEquals(10, allDegrees.size());
        assertThat(allDegrees)
                .extracting(Degree::getName)
                .containsExactlyInAnyOrder(
                        "Ingenieria Informática",
                        "Quimicas",
                        "Derecho",
                        "Ingenieria Industrial",
                        "Marketing",
                        "Física",
                        "ADE",
                        "Bioinformática",                        
                        "Informática de Gestión",
                        "Informática de Sistemas");
    }
    
    @Test
    void testFindById() {
        Optional <Degree> degree = degreeRepo.findById(1);
        assertEquals("Ingenieria Informática", degree.get().getName());
        assertTrue("Ingenieria Informática".equalsIgnoreCase(degree.get().getName()));
    }
    
    @Test
    void testCount() {
        assertEquals(10, degreeRepo.count());
    }

}
