/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.persistence;

import com.ldf.exam.model.Exam;
import com.ldf.exam.model.ExamQuestion;
import java.util.List;

/**
 *
 * @author Lo
 */
public interface ExamRepoCustom {
    public List<Exam> findByQuestionTypeAndCourseCriteria(
            Integer idCourse, 
            ExamQuestion.QuestionType questionType1, 
            ExamQuestion.QuestionType questionType2);
}
