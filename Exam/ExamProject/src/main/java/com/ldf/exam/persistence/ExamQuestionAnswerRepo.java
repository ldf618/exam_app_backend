/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.persistence;

import com.ldf.exam.model.ExamQuestionAnswer;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Lo
 */
public interface ExamQuestionAnswerRepo
    extends CrudRepository<ExamQuestionAnswer, Integer> {
}