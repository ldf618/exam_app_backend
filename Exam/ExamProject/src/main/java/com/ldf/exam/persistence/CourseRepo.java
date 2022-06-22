/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.persistence;

import com.ldf.exam.model.Course;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Lo
 */
public interface CourseRepo
        extends CrudRepository<Course, Integer> {

    @Query("select distinct co from Student st inner join st.classrooms cr inner join cr.course co inner join co.degree de "
            + "where st.id=:idStudent and de.id=:idDegree ")
    public List<Course> findCoursesByStudentIdAndDegreeId
        (@Param("idStudent") Integer idStudent,
         @Param("idDegree") Integer idDegree);
        
    @Query("select distinct co from Consultant con inner join con.classrooms cr inner join cr.course co inner join co.degree de "
            + "where con.id=:idConsultant and de.id=:idDegree ")
    public List<Course> findCoursesByConsultantIdAndDegreeId
        (@Param("idConsultant") Integer idConsultant,
         @Param("idDegree") Integer idDegree);        
}
