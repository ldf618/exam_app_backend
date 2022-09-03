/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ldf.exam.controller.dto;

import com.ldf.exam.model.Exam;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchExamDTO {

    private String name;
    private Exam.ExamType type; 
    private Boolean published; 
    private LocalDate startDate; 
    private LocalDate endDate;
}
