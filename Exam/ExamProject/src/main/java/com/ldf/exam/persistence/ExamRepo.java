/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.persistence;

import com.ldf.exam.model.Exam;
import com.ldf.exam.model.ExamQuestion;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 *
 * @author Lo
 */
public interface ExamRepo extends JpaRepository<Exam, Integer> {
    
    public List<Exam> findByCourse(int idCourse);
    public List<Exam> findByPublicationDateIsNotNullAndPublicationDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("select distinct e from Exam e inner join e.examQuestions eq "
            + "where e.publicationDate is not null "
            + "and e.course.id=:idCourse and eq.type in (:questionType1,:questionType2)")
    public List<Exam> findByQuestionTypeAndCourse(
            @Param("idCourse") Integer idCourse,
            @Param("questionType1") ExamQuestion.QuestionType questionType1, 
            @Param("questionType2") ExamQuestion.QuestionType questionType2);
        
}
