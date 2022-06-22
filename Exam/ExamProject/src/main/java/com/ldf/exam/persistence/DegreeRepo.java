/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.persistence;

import com.ldf.exam.model.Degree;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Lo
 */
public interface DegreeRepo extends CrudRepository<Degree, Integer> {
     public Integer countByNameIgnoreCase(String name);
     public Boolean existsDegreeByNameIgnoreCase(String name);
     
      @Query("select distinct de from Student st inner join st.classrooms cr inner join cr.course co inner join co.degree de "
            + "where st.id=:idStudent ")
    public List<Degree> findDegreesByStudentId(@Param("idStudent") Integer idStudent);
    
      @Query("select distinct de from Consultant con inner join con.classrooms cr inner join cr.course co inner join co.degree de "
            + "where con.id=:idConsultant ")
    public List<Degree> findDegreesByConsultantId(@Param("idConsultant") Integer idConsultant);    
}
