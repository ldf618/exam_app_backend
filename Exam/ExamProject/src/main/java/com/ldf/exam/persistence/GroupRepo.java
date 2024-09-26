/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.persistence;

import com.ldf.exam.model.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Lo
 */
public interface GroupRepo
    extends CrudRepository<Group, Integer> {
        
     @Query("select distinct gr.id as id, gr.name as name "
             + "from Group gr "
             + "inner join gr.classroom cr "
             + "inner join gr.students st "             
             + "inner join cr.course co "
             + "where st.id=:idStudent and co.id=:idCourse ")
    public Object[] findGroupByStudentIdAndCourseId
        (@Param("idStudent") Integer idStudent,
         @Param("idCourse") Integer idCourse);
}
