/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ldf.exam.persistence;

import com.ldf.exam.model.ExamAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lo
 */
public interface ExamAnswerRepo
    extends JpaRepository<ExamAnswer, Integer> {
}
