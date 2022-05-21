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


/**
 *
 * @author Lo
 */
public interface ExamRepo extends JpaRepository<Exam, Integer> {
    
    public List<Exam> findByCourse(int idCourse);
    public List<Exam> findByPublicationDateIsNotNullAndPublicationDateBetween(LocalDate startDate, LocalDate endDate);
    //public List<Exam> findByQuestionTypeAndCourse(Integer idCourse, ExamQuestion.QuestionType questionType1, ExamQuestion.QuestionType questionType2);
        
}
