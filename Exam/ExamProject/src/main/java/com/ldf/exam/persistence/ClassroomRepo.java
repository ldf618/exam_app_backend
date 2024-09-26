/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.persistence;

import com.ldf.exam.model.Classroom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Lo
 */
public interface ClassroomRepo
    extends CrudRepository<Classroom, Integer> {
    
     @Query("select distinct cr.id, cr.name from Student st inner join st.classrooms cr inner join cr.course co "
            + "where st.id=:idStudent and co.id=:idCourse ")
    public Object[] findClassroomByStudentIdAndCourseId
        (@Param("idStudent") Integer idStudent,
         @Param("idCourse") Integer idCourse);
     /*  
     @Query("select distinct cr.id, cr.name from Consultant con inner join con.classrooms cr inner join cr.course co "
            + "where con.id=:idStudent and co.id=:idCourse ")
    public Object[] findClassroomByConsultantIdAndCourseId
        (@Param("idStudent") Integer idStudent,
         @Param("idCourse") Integer idCourse);
       */ 
    @Query("select distinct cr.id, cr.name from Classroom cr inner join cr.consultant con inner join cr.course co "
   + "where con.id=:idStudent and co.id=:idCourse ")
        public Object[] findClassroomByConsultantIdAndCourseId
                    (@Param("idStudent") Integer idStudent,
         @Param("idCourse") Integer idCourse);
       
}
